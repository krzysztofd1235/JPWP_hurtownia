from flask import jsonify, abort, request
from hurtownia import app, db
from hurtownia.models import *
from hurtownia.lib import error, success
@app.route('/categories',  methods = ["POST","GET","DELETE","PATCH"])
def categories():
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            try:
                name = data['name']
                description = data['description']
                parent_id = data['parent_id']
            except:
                return error("Invalid JSON")
            record = Category(name, description, parent_id)
            db.session.add(record)
            try:
                db.session.commit()    
            except:
                return error("DB error when adding category")
            return success(id=record.id)
    elif request.method == 'GET':
        categories = []
        for category in Category.query.all():
            categories.append({
                "id":category.id, 
                "name":category.name, 
                "description":category.description,
                "parent_id":category.parent_id
                })
        return jsonify(categories)
    elif request.method == 'DELETE':
        if request.is_json:
            try:
                data = request.get_json()
                id = data['id']
            except:
                return error("Invalid JSON")
            to_delete = Category.query.filter_by(id=id).first()
            if to_delete is None:
                return error("Category not found")
            if to_delete.parent_id is None:
                return error("Cannot delete main category")
            db.session.delete(to_delete)
            try:
                db.session.commit()    
            except Exception as e:
                return error("DB error when deleting category "+str(e))
            return success()
    elif request.method == 'PATCH':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
                name = data['name']
                description = data['description']
                parent_id = data['parent_id']
            except:
                return error("Invalid JSON")
            category = Category.query.filter_by(id=data["id"]).first()
            if category is None:
                return error("Category not found")
            category.name = name
            category.description = description
            category.parent_id = parent_id
            try:
                db.session.commit()    
            except:
                return error("DB error when updating category")
            return success()
    abort(404)

@app.route('/products', methods = ["POST","GET","DELETE","PATCH"])
def products():
    app.logger.info(request)
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            try:
                name = data['name']
                category_id = data['category_id']
                description = data['description']
                sell_price = data['sell_price']
                buy_price = data['buy_price']
                tax_id = data['tax_id']
                unit_id = data['unit_id']
                barcode = data['barcode']
            except:
                return error("Invalid JSON")
            record = Product(name, category_id,description,sell_price,tax_id,buy_price,unit_id,barcode)
            db.session.add(record)
            try:
                db.session.commit()    
            except:
                return error("DB error when adding product")
            places = Place.query.all()
            for place in places:
                stock = Stock(record.id, place.id)
                db.session.add(stock)
            try:
                db.session.commit()    
            except:
                return error("DB error when adding stocks")
            return success(id=record.id)
    elif request.method == 'GET':
        products = []
        for product in Product.query.all():
            products.append({
                "id":product.id, 
                "name":product.name, 
                "description":product.description,
                "sell_price":product.sell_price,
                "buy_price":product.buy_price,
                "tax_id":product.tax_id,
                "unit_id":product.unit_id,
                "barcode":product.barcode,
                "cat_id":product.category_id
                })
        return jsonify(products)
    elif request.method == 'DELETE':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
            except:
                return error("Invalid JSON")
            to_delete = Product.query.filter_by(id=data['id']).first()
            if to_delete is None:
                return error("Product not found")
            db.session.delete(to_delete)
            try:
                db.session.commit()    
            except:
                return error("DB error when deleting product")
            return success()
    elif request.method == 'PATCH':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
                name = data['name']
                category_id = data['category_id']
                description = data['description']
                sell_price = data['sell_price']
                buy_price = data['buy_price']
                tax_id = data['tax_id']
                unit_id = data['unit_id']
                barcode = data['barcode']
            except:
                return error("Invalid JSON")
            product = Product.query.filter_by(id=data["id"]).first()
            if product is None:
                return error("Product not found")
            product.name = name
            product.category_id = category_id
            product.description = description
            product.sell_price = sell_price
            product.buy_price = buy_price
            product.tax_id = tax_id
            product.unit_id = unit_id
            product.barcode = barcode
            try:
                db.session.commit()    
            except:
                return error("DB error when updating product")
            return success()
    abort(404)
    

@app.route('/stocks', methods = ["GET","PATCH"])
def stocks():
    if request.method == 'GET':
        stocks = []
        for stock in Stock.query.all():
            stocks.append({
                "id":stock.id, 
                "product_id":stock.product_id, 
                "place_id":stock.place_id,
                "quantity":stock.quantity
                })
        return jsonify(stocks)
    elif request.method == 'PATCH':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
                quantity = data['quantity']
            except:
                return error("Invalid JSON")
            stock = Stock.query.filter_by(id=data['id']).first()
            stock.quantity = data['quantity']
            if stock is None:
                return error("Stock not found")
            try:
                db.session.commit()    
            except:
                return error("DB error when updating stock")
            return success()
    abort(404)

@app.route('/places', methods = ["GET","POST","DELETE"])
def places():
    if request.method == 'GET':
        places = []
        for place in Place.query.all():
            places.append({
                "id":place.id, 
                "name":place.name, 
                "description":place.description
                })
        return jsonify(places)
    elif request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            try:
                name = data['name']
                description = data['description']
            except:
                return error("Invalid JSON")
            place = Place(data['name'], data['description'])
            db.session.add(place)
            try:
                db.session.commit()    
            except:
                return error("DB error when creating place")
            products = Product.query.all()
            for product in products:
                stock = Stock(product.id, place.id)
                db.session.add(stock)
            try:
                db.session.commit()    
            except:
                return error("DB error when creating stocks for place")
            return success(id=place.id)
    elif request.method == 'DELETE':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
            except:
                return error("Invalid JSON")
            place = Place.query.filter_by(id=data['id']).first()
            db.session.delete(place)
            try:
                db.session.commit()    
            except:
                return error("DB error when deleting place")
            return success()
    abort(404)

@app.route('/units',  methods = ["POST","GET","DELETE","PATCH"])
def units():
    units = []
    for unit in Unit.query.all():
        units.append({
            "id":unit.id, 
            "name":unit.name, 
            "shortcut":unit.shortcut, 
            "divider":unit.divider
            })
    return jsonify(units)

@app.route('/taxes')
def taxes():
    taxes = []
    for tax in Tax.query.all():
        taxes.append({
            "id":tax.id, 
            "name":tax.name, 
            "percent":tax.percent
            })
    return jsonify(taxes)

@app.route('/clients', methods = ["POST","GET","DELETE","PATCH"])
def clients():
    app.logger.info(request)
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            try:
                f_name = data["f_name"]
                s_name = data["s_name"]
                company = data["company"]
                nip = data["nip"]
                email = data["email"]
            except:
                return error("Invalid JSON")
            record = Client(f_name, s_name, company, nip, email)
            db.session.add(record)
            try:
                db.session.commit()    
            except:
                return error("DB error when adding client")
            return success(id=record.id)
    elif request.method == 'GET':
        clients = []
        for client in Client.query.all():
            clients.append({
                "id":client.id,
                "f_name":client.f_name,
                "s_name":client.s_name,
                "company":client.company,
                "nip":client.nip,
                "email":client.email
                })
        return jsonify(clients)
    elif request.method == 'DELETE':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
            except:
                return error("Invalid JSON")
            to_delete = Client.query.filter_by(id=data['id']).first()
            if to_delete is None:
                return error("Client not found")
            db.session.delete(to_delete)
            try:
                db.session.commit()    
            except:
                return error("DB error when deleting client")
            return success()
    elif request.method == 'PATCH':
        if request.is_json:
            data = request.get_json()
            try:
                id = data["id"]
                f_name = data["f_name"]
                s_name = data["s_name"]
                company = data["company"]
                nip = data["nip"]
                email = data["email"]
            except:
                return error("Invalid JSON")
            client = Client.query.filter_by(id=id).first()
            if client is None:
                return error("Client not found")
            client.f_name = f_name
            client.s_name = s_name
            client.company = company
            client.nip = nip
            client.email = email
            try:
                db.session.commit()    
            except:
                return error("DB error when updating client")
            return success()
    abort(404)


@app.route('/orders', methods = ["POST","GET","DELETE","PATCH"])
def orders():
    app.logger.info(request)
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            try:
                client = data["client"]
            except:
                return error("Invalid JSON")
            record = Order(client)
            db.session.add(record)
            try:
                db.session.commit()    
            except Exception as e:
                print(e)
                return error("DB error when adding order")
            return success(id=record.id, code=record.code)
    elif request.method == 'GET':
        orders = []
        for order in Order.query.all():
            orders.append({
                "id":order.id,
                "client":order.client_id,
                "status":order.status,
                "code":order.code
                })
        return jsonify(orders)
    elif request.method == 'DELETE':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
            except:
                return error("Invalid JSON")
            to_delete = Order.query.filter_by(id=data['id']).first()
            if to_delete is None:
                return error("Order not found")
            db.session.delete(to_delete)
            try:
                db.session.commit()    
            except:
                return error("DB error when deleting order")
            return success()
    elif request.method == 'PATCH':
        if request.is_json:
            data = request.get_json()
            try:
                id = data["id"]
                status = data["status"]
            except:
                return error("Invalid JSON")
            order = Order.query.filter_by(id=data["id"]).first()
            if order is None:
                return error("Order not found")
            order.status = status
            try:
                db.session.commit()    
            except:
                return error("DB error when updating order")
            return success()
    abort(404)


@app.route('/cartitems', methods = ["POST","GET","DELETE","PATCH"])
def cartitems():
    app.logger.info(request)
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            try:
                product = data["product"]
                stock = data["stock"]
                quantity = data["quantity"]
                price = data["price"]
                order = data["order"]
            except Exception as e:
                print(e)
                return error("Invalid JSON")
            if Product.query.filter_by(id=product).count() < 1:
                return error("Invalid product")
            if Order.query.filter_by(id=order).count() < 1:
                return error("Invalid order")
            stock = Stock.query.filter_by(id=stock).first()
            if stock is None:
                return error("Invalid stock")
            if stock.quantity < quantity:
                return error("Not enough items in stock")
            stock.quantity -= quantity
            record = CartItem(order, product, quantity, price)
            db.session.add(record)
            try:
                db.session.commit()    
            except Exception as e:
                print(e)
                return error("DB error when adding cart item")
            return success(id=record.id)
    elif request.method == 'GET':
        cartitems = []
        for cartitem in CartItem.query.all():
            cartitems.append({
                "id":cartitem.id,
                "order":cartitem.order_id,
                "product":cartitem.product_id,
                "quantity":cartitem.quantity,
                "price":cartitem.price
                })
        return jsonify(cartitems)
    elif request.method == 'DELETE':
        if request.is_json:
            data = request.get_json()
            try:
                id = data['id']
                stock = data['stock']
            except:
                return error("Invalid JSON")
            to_delete = CartItem.query.filter_by(id=data['id']).first()
            if to_delete is None:
                return error("Order not found")
            stock = Stock.query.filter_by(id=stock).first()
            if stock is None:
                return error("Stock not found")
            stock.quantity += to_delete.quantity
            db.session.delete(to_delete)
            try:
                db.session.commit()    
            except:
                return error("DB error when deleting cartitem")
            return success()
    elif request.method == 'PATCH':
        if request.is_json:
            data = request.get_json()
            try:
                id = data["id"]
                quantity = data["quantity"]
                stock = data["stock"]
            except:
                return error("Invalid JSON")
            cartitem = CartItem.query.filter_by(id=data["id"]).first()
            if cartitem is None:
                return error("Cartitem not found")
            stock = Stock.query.filter_by(id=data["stock"]).first()
            if stock is None:
                return error("Cartitem not found")
            if cartitem.quantity > quantity:
                stock.quantity += cartitem.quantity - quantity
                cartitem.quantity = quantity
            elif cartitem.quantity < quantity:
                if stock.quantity >= quantity - cartitem.quantity:
                    stock.quantity -= quantity - cartitem.quantity 
                    cartitem.quantity = quantity
                else:
                    return error("Not enough items in stock")
            else:
                return error("Nothing to update")
            try:
                db.session.commit()    
            except:
                return error("DB error when updating order")
            return success()
    abort(404)
    