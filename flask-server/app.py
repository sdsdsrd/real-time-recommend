from flask import Flask
from flask_restful import Api
from item_based_collaborative_filtering import (
    ItemBasedCollaborativeFiltering,
)

app = Flask(__name__)
api = Api(app)


api.add_resource(ItemBasedCollaborativeFiltering, "/<contentId>")


if __name__ == "__main__":
    app.run()