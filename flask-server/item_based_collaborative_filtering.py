from flask_restful import Resource
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
import json


class ItemBasedCollaborativeFiltering(Resource):
    ratings = pd.read_csv("ratings.csv")
    contents = pd.read_csv("contents.csv")
    stb_content_rating = pd.merge(ratings, contents, on="epsdId")

    content_stb_rating = stb_content_rating.pivot_table(
        "rating", index="epsdId", columns="stbId"
    )

    content_stb_rating.fillna(0, inplace=True)

    item_based_collabor = cosine_similarity(content_stb_rating)

    item_based_collabor = pd.DataFrame(
        data=item_based_collabor,
        index=content_stb_rating.index,
        columns=content_stb_rating.index,
    )

    def get(self, epsdId):
        # print(contentId)
        # print(self.item_based_collabor.columns)
        result = (
            self.item_based_collabor[epsdId]
            .sort_values(ascending=False)[:8]
            .to_json(orient="index")
        )
        parsed = json.loads(result)
        return list(parsed.keys())
