from flask import Flask


app = Flask(__name__)


@app.route("/")
def index():
    return "flask test 성공"


if __name__ == "__main__":
    app.run()