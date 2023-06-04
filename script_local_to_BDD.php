<?php
// Récupération des données JSON envoyées par le client
$json = file_get_contents('php://input');
$data = json_decode($json);

// Connexion à la base de données MySQL avec PDO
$host = 'localhost';
$user = 'root';
$password = 'root';
$database = 'gestculture';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$database;charset=utf8", $user, $password);
} catch (PDOException $e) {
    die("Error: " . $e->getMessage());
}

// Traitement des données de la parcelle
if (isset($data->parcelles)) {
    $parcelles = $data->parcelles;
    $sql = "INSERT INTO parcelle (Annee, IdExploitation, IdParcelle, surface, decoupage, RendementPrevu, RendementReel, code) VALUES (:annee, :idExploitation, :idParcelle, :surface, :decoupage, :rendementPrevu, :rendementReel, :code)";
    $stmt = $pdo->prepare($sql);
    foreach ($parcelles as $parcelle) {
        $stmt->execute([
            'annee' => $parcelle->Annee,
            'idExploitation' => $parcelle->IdExploitation,
            'idParcelle' => $parcelle->IdParcelle,
            'surface' => $parcelle->surface,
            'decoupage' => $parcelle->decoupage,
            'rendementPrevu' => $parcelle->RendementPrevu,
            'rendementReel' => $parcelle->RendementReel,
            'code' => $parcelle->code
        ]);
    }
}

// Traitement des données de la visite
if (isset($data->visiter)) {
    $visites = $data->visiter;
    $sql = "INSERT INTO visiter (Matricule, IdExploitation, Annee) VALUES (:matricule, :idExploitation, :annee)";
    $stmt = $pdo->prepare($sql);
    foreach ($visites as $visite) {
        $stmt->execute([
            'matricule' => $visite->Matricule,
            'idExploitation' => $visite->IdExploitation,
            'annee' => $visite->Annee
        ]);
    }
}

// Fermeture de la connexion à la base de données
$pdo = null;

// Réponse au client
$response = array('status' => 'ok');
echo json_encode($response);
?>
