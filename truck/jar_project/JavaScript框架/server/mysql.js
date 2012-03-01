/*≤‚ ‘”√¿˝*/

var mysql = require('mysql-libmysqlclient'), conn;

conn = mysql.createConnectionSync();
conn.connectSync('host', 'user', 'password', 'database', port);
conn.setCharsetSync('utf8');

if (!conn.connectedSync()) {
    console.log('no connection');
    process.exit(1);
}


conn.query("SELECT * FROM " + table_name + ";", function (err, res) {
    if (err) {
        throw err;
    }

    res.fetchAll(function (err, rows) {
        if (err) {
            throw err;
        }
        console.log("rows in table_name %j", res);
    });
});


process.on('exit', function () {
  conn.closeSync();
});
