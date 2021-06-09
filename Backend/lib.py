def error(message):
    return {"message":message}
def success(**kwargs):
    resp = {"message":"OK"}
    for arg in kwargs:
        resp[arg] = kwargs[arg]
    print(resp)
    return resp