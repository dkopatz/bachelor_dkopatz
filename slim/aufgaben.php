<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

$app = new \Slim\App;

//$app->options('/{routes:.+}', function ($request, $response, $args) {
//    return $response;
//});

$app->add(function ($req, $res, $next) {
    $response = $next($req, $res);
    return $response
            ->withHeader('Access-Control-Allow-Origin', '*')
            ->withHeader('Access-Control-Allow-Headers', 'X-Requested-With, Content-Type, Accept, Origin, Authorization')
            ->withHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
});

//Get getränke inventur von Datum
$app->get('/api/ginv/{datum}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');

    $sql = "SELECT Stand_nr FROM getränke_inventur WHERE Datum = $datum GROUP BY Stand_nr";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

// Update getränke inventur
$app->put('/api/ginv/update/{datum}/{stand}/{sorte}/{menge}', function(Request $request, Response $response){

    $datum = $request->getAttribute('datum');
    $stand = $request->getAttribute('stand');
    $sorte = $request->getAttribute('sorte');
    $menge = $request->getAttribute('menge');


    //echo $aufgabe;
    //echo $status;
    $sql = "UPDATE getränke_inventur SET
				Menge = $menge

			WHERE Datum = $datum AND Stand_nr = $stand AND Sorte = $sorte";

    try{
        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->prepare($sql);

        $stmt->execute();

        echo '{"notice": {"text": "Best Updated"}}';

    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});


//Get getränke Inventur from multiple Ständen
$app->get('/api/ginven/{datum}/{stand}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');
  $stand = $request->getAttribute('stand');
  if($stand == '"Alle"'){
    $stand = '"%"';
  }
  if($stand == '"Norden"'){
    $stand = '"N%"';
  }
 if($stand == '"Osten"'){
    $stand = '"O%"';
  }
 if($stand == '"Süden"'){
    $stand = '"S%"';
  }
 if($stand == '"Westen"'){
    $stand = '"W%"';
  }
 if($stand == '"Bier Caddy"'){
    $stand = '"BC%"';
  }
 

    $sql = "SELECT Sorte, sum(Menge) as Menge FROM getränke_inventur WHERE Datum = $datum AND Stand_nr LIKE $stand GROUP BY Position";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});


//Get all ginv. Daten
$app->get('/api/ginvdat', function(Request $request, Response $response){
    $sql = "SELECT Datum FROM getränke_inventur GROUP BY Datum";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

// Add Aufgabe
$app->post('/api/inv/add/{datum}/{kategorie}/{produkt}/{anzahl}/{einheit}/{bemerkung}', function(Request $request, Response $response){
   $datum = $request->getAttribute('datum');
   $kategorie = $request->getAttribute('kategorie');
   $produkt = $request->getAttribute('produkt');
   $anzahl = $request->getAttribute('anzahl');
   $einheit = $request->getAttribute('einheit'); 
   $bemerkung = $request->getAttribute('bemerkung');


    $sql = "INSERT INTO inventurlisten_items (Datum, Kategorie, Produkt, Anzahl, Einheit, Bemerkung) VALUES ($datum, $kategorie, $produkt, $anzahl, $einheit,$bemerkung )";

    try{
        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->prepare($sql);

        $stmt->bindParam(':aufgabe', $aufgabe);


        $stmt->execute();

        echo '{"notice": {"text": "Inventuritem Added"}';

    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});


// Update inventur
$app->put('/api/inv/update/{datum}/{produkt}/{anzahl}/{bemerkung}', function(Request $request, Response $response){

    $datum = $request->getAttribute('datum');
    $produkt = $request->getAttribute('produkt');
    $anzahl = $request->getAttribute('anzahl');
    $bemerkung = $request->getAttribute('bemerkung');

    //echo $aufgabe;
    //echo $status;
    $sql = "UPDATE inventurlisten_items SET
				Anzahl = $anzahl,
				Bemerkung = $bemerkung

			WHERE Datum = $datum AND Produkt = $produkt";

    try{
        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->prepare($sql);

        $stmt->execute();

        echo '{"notice": {"text": "Inv Updated"}}';

    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});


//Get Inventuritems von Datum + Kategorie
$app->get('/api/inv/{datum}/{kategorie}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');
  $kategorie = $request->getAttribute('kategorie');

  if($kategorie == '"Alle"'){
    $kategorie = '"%"';
  }
    $kategorie = str_replace("~", "/", $kategorie);

    $sql = "SELECT Datum, Produkt, Anzahl, Einheit, Bemerkung FROM inventurlisten_items WHERE Datum = $datum AND Kategorie LIKE $kategorie ORDER BY Kategorie, Produkt";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

//Get Inventurkategorien von Datum
$app->get('/api/inv/{datum}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');

    $sql = "SELECT Datum, Kategorie FROM inventurlisten_items WHERE Datum = $datum GROUP BY Kategorie";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

//Get all inv. Daten
$app->get('/api/invdat', function(Request $request, Response $response){
    $sql = "SELECT Datum FROM inventurlisten_items GROUP BY Datum";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

//Get Bestueckung von Datum
$app->get('/api/best/{datum}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');

    $sql = "SELECT Stand_nr FROM bestueckungs_items WHERE Datum = $datum GROUP BY Stand_nr";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

// Update bestueckung
$app->put('/api/best/update/{datum}/{stand}/{sorte}/{menge}', function(Request $request, Response $response){

    $datum = $request->getAttribute('datum');
    $stand = $request->getAttribute('stand');
    $sorte = $request->getAttribute('sorte');
    $menge = $request->getAttribute('menge');


    //echo $aufgabe;
    //echo $status;
    $sql = "UPDATE bestueckungs_items SET
				Menge = $menge

			WHERE Datum = $datum AND Stand_nr = $stand AND Sorte = $sorte";

    try{
        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->prepare($sql);

        $stmt->execute();

        echo '{"notice": {"text": "Best Updated"}}';

    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});


//Get Bestueckung from multiple Ständen
$app->get('/api/besten/{datum}/{stand}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');
  $stand = $request->getAttribute('stand');
  if($stand == '"Alle"'){
    $stand = '"%"';
  }
  if($stand == '"Norden"'){
    $stand = '"N%"';
  }
 if($stand == '"Osten"'){
    $stand = '"O%"';
  }
 if($stand == '"Süden"'){
    $stand = '"S%"';
  }
 if($stand == '"Westen"'){
    $stand = '"W%"';
  }
 if($stand == '"Bier Caddy"'){
    $stand = '"BC%"';
  }
 

    $sql = "SELECT Sorte, sum(Menge) as Menge FROM bestueckungs_items WHERE Datum = $datum AND Stand_nr LIKE $stand GROUP BY Position";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});



//Get Bestueckung von Datum + Stand
$app->get('/api/best/{datum}/{stand}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');
  $stand = $request->getAttribute('stand');

    $sql = "SELECT Sorte, Menge FROM bestueckungs_items WHERE Datum = $datum AND Stand_nr LIKE $stand ORDER BY Position";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

//Get all best. Daten
$app->get('/api/bestdat', function(Request $request, Response $response){
    $sql = "SELECT Datum FROM bestueckungs_items GROUP BY Datum";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});




//Get all todo Daten
$app->get('/api/tododat', function(Request $request, Response $response){
    $sql = "SELECT Datum FROM todoliste GROUP BY Datum";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

//Get All Aufgaben in Datum
$app->get('/api/todo/{datum}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');

    $sql = "SELECT * FROM todoliste WHERE Datum = $datum AND Status = 'ungelöst' ORDER BY Priorität desc";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

// Get All fertigen Aufgaben
$app->get('/api/todone/{datum}', function(Request $request, Response $response){
  $datum = $request->getAttribute('datum');
  
    $sql = "SELECT Aufgabe, Status, gelost_von FROM todoliste WHERE Status = 'gelöst' AND Datum = $datum";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);
        $todo = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($todo, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

// Update todo
$app->put('/api/todo/update/{datum}/{aufgabe}/{user}', function(Request $request, Response $response){
    $datum = $request->getAttribute('datum');
    $aufgabe = $request->getAttribute('aufgabe');
    $aufgabe = str_replace("uuee", "ü", $aufgabe);
    $aufgabe = str_replace("UUEE", "Ü", $aufgabe);
    $aufgabe = str_replace("ooee", "ö", $aufgabe);
    $aufgabe = str_replace("OOEE", "Ö", $aufgabe);
    $aufgabe = str_replace("aaee", "ä", $aufgabe);
    $aufgabe = str_replace("AAEE", "Ä", $aufgabe);
    $aufgabe = str_replace("sszz", "ß", $aufgabe);

    $user = $request->getAttribute('user');
    $user = str_replace("uuee", "ü", $user);
    $user = str_replace("UUEE", "Ü", $user);
    $user = str_replace("ooee", "ö", $user);
    $user = str_replace("OOEE", "Ö", $user);
    $user = str_replace("aaee", "ä", $user);
    $user = str_replace("AAEE", "Ä", $user);
    $user = str_replace("sszz", "ß", $user);
    $status = $request->getParam('Status');

    //echo $aufgabe;
    //echo $status;
    $sql = "UPDATE todoliste SET
				Status 	= :Status,
				gelost_von = $user

			WHERE Aufgabe = $aufgabe AND Datum = $datum COLLATE utf8_unicode_ci";

    try{
        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->prepare($sql);

        $stmt->bindParam(':Status', $status);

        $stmt->execute();

        echo '{"notice": {"text": "ToDo Updated"}}';

    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

// Add Aufgabe
$app->post('/api/todo/add/{datum}/{aufgabe}', function(Request $request, Response $response){
   $datum = $request->getAttribute('datum');
   $aufgabe = $request->getAttribute('aufgabe');


    $sql = "INSERT INTO todoliste (Datum, Aufgabe, Priorität) VALUES ($datum, $aufgabe, 10)";

    try{
        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->prepare($sql);

        $stmt->bindParam(':aufgabe', $aufgabe);


        $stmt->execute();

        echo '{"notice": {"text": "Aufgabe Added"}';

    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});

$app->get('/api/login/{username}/{password}', function(Request $request, Response $response){
  $username = $request->getAttribute('username');
  $password = $request->getAttribute('password');

    $sql = "SELECT username, password FROM login WHERE username = $username AND password = $password";

    try{

        // Get DB Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);

        $login = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;

        echo json_encode($login, JSON_UNESCAPED_UNICODE);
    } catch(PDOException $e){
        echo '{"error": {"text": '.$e->getMessage().'}';
    }
});
