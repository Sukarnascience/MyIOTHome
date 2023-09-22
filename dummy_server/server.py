from flask import Flask, jsonify
import random

app = Flask(__name__)

@app.route('/dht_data')
def get_dht_data():
    temperature = random.randint(0, 100)
    humidity = random.randint(0, 100)
    
    data = {
        "device_id":"ESP19092023",
        "status": 200,
        "temperature": temperature,
        "humidity": humidity
    }
    
    return jsonify(data)

# Handle 404 errors
@app.errorhandler(404)
def not_found_error(error):
    error_data = {
        "device_id":"ESP19092023",
        "status": 404
    }
    return jsonify(error_data), 404

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3000)
