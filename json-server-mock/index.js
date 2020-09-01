const jsonServer = require('json-server')
const server = jsonServer.create()

server.get('/token/check', (req, res) => {
	res.jsonp({
		"name" : "username",
		"role" : "ROLE_USER"
	})
})

server.post('/token', (req, res) => {
	res.jsonp({
		"token" : "dk3jSlDKkld444kDj23kdadkDDDDododsdsjasd23nladkiiiii021"
	})
})

server.listen(process.env.PORT, () => {
	console.log('Auth Server (mock) is running')
})