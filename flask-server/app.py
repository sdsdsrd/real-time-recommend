from flask import Flask
from flask_restful import Api
from item_based_collaborative_filtering import (
    ItemBasedCollaborativeFiltering,
)

app = Flask(__name__)
api = Api(app)


api.add_resource(ItemBasedCollaborativeFiltering, "/<epsdId>")


if __name__ == "__main__":
    app.run(host='0.0.0.0')