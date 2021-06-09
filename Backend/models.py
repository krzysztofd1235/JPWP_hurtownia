
from hurtownia import db
from sqlalchemy.orm import backref
import uuid

class Tax(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    percent = db.Column(db.Integer(), nullable=False)
    name = db.Column(db.String(length=128), nullable=False)
    products = db.relationship('Product', backref='tax', lazy=True)

    def __init__(self, name, percent):
        self.name = name
        self.percent = percent


class Unit(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    name = db.Column(db.String(length=32), nullable=False)
    shortcut = db.Column(db.String(length=5), nullable=False)
    divider = db.Column(db.Integer(), nullable=False)
    products = db.relationship('Product', backref='unit', lazy=True)


class Category(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    name = db.Column(db.String(length=128), nullable=False)
    description = db.Column(db.String(length=512), nullable=False)
    parent_id = db.Column(db.Integer(), db.ForeignKey('category.id', ondelete='CASCADE'), index=True, nullable=True)
    parent = db.relationship(lambda: Category, remote_side=id, backref=backref("parent_category", cascade="all, delete-orphan"))
    products = db.relationship("Product", cascade="all,delete", backref="category")

    def __init__(self, name, description, parent_id):
        self.name = name
        self.description = description
        self.parent_id = parent_id


class Product(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    name = db.Column(db.String(length=128), nullable=False)
    category_id = db.Column(db.Integer, db.ForeignKey('category.id', ondelete='CASCADE'), nullable=False)
    description = db.Column(db.String(length=512), nullable=False)
    sell_price = db.Column(db.Integer(), nullable=False)
    tax_id = db.Column(db.Integer, db.ForeignKey('tax.id'), nullable=False)
    buy_price = db.Column(db.Integer(), nullable=False)
    unit_id = db.Column(db.Integer, db.ForeignKey('unit.id'), nullable=False)
    barcode = db.Column(db.String(length=64), nullable=False, unique=True)
    stocks = db.relationship('Stock', cascade="all,delete", backref='product', lazy=True)

    def __init__(self, name, category_id, description, sell_price, tax_id, buy_price, unit_id, barcode):
        self.name = name
        self.category_id = category_id
        self.description = description
        self.sell_price = sell_price
        self.tax_id = tax_id
        self.buy_price = buy_price
        self.unit_id = unit_id
        self.barcode = barcode


class Place(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    name = db.Column(db.String(length=128), nullable=False)
    description = db.Column(db.String(length=512), nullable=False)
    stocks = db.relationship('Stock', cascade="all,delete", backref='place', lazy=True)

    def __init__(self, name, description):
        self.name = name
        self.description = description


class Stock(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    product_id = db.Column(db.Integer, db.ForeignKey('product.id', ondelete='CASCADE'), nullable=False)
    place_id = db.Column(db.Integer, db.ForeignKey('place.id', ondelete='CASCADE'), nullable=False)
    quantity = db.Column(db.Integer(), nullable=False)

    def __init__(self, product_id, place_id, quantity=0):
        self.product_id = product_id
        self.place_id = place_id
        self.quantity = quantity


class Client(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    f_name = db.Column(db.String(length=512), nullable=False)
    s_name = db.Column(db.String(length=512), nullable=False)
    company = db.Column(db.String(length=512), nullable=False)
    nip = db.Column(db.String(length=512), nullable=False)
    email = db.Column(db.String(length=512), nullable=False)
    orders = db.relationship('Order', cascade="all,delete", backref='client', lazy=True)

    def __init__(self, f_name, s_name, company, nip, email):
        self.f_name = f_name
        self.s_name = s_name
        self.company = company
        self.nip = nip
        self.email = email


class Order(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    code = db.Column(db.String(length=256), nullable=False)
    client_id = db.Column(db.Integer, db.ForeignKey('client.id'), nullable=False)
    status = db.Column(db.Integer(), nullable=False)
    items = db.relationship('CartItem', cascade="all,delete", backref='order', lazy=True)

    def __init__(self, client_id):
        self.client_id = client_id
        self.status = 0 
        self.code = str(uuid.uuid4())


class CartItem(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    order_id = db.Column(db.Integer, db.ForeignKey('order.id', ondelete='CASCADE'), nullable=False)
    product_id = db.Column(db.Integer, db.ForeignKey('product.id', ondelete='CASCADE'), nullable=False)
    quantity = db.Column(db.Integer(), nullable=False)
    price = db.Column(db.Integer(), nullable=True)

    def __init__(self, order_id, product_id, quantity, price):
        self.order_id = order_id
        self.product_id = product_id
        self.quantity = quantity 
        self.price = price