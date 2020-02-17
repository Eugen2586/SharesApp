package com.example.sharesapp.Functions.Persistenz;

public class LoadFromJson {

    private VBox workspace;
    Projektview p;

    public void setFenster(Anzeige_Fenster fenster) {
        this.fenster = fenster;
    }

    private Anzeige_Fenster fenster;

    public void setH(HerkulesFenster h) {
        this.h = h;
    }

    HerkulesFenster h;

    public void readJson() throws IOException, ParseException {
        FileReader fr = new FileReader("keep.dat");
        BufferedReader br = new BufferedReader(fr);
        String st = new String();
        String line = new String();
        while(line != null) {
            line = br.readLine();
            if(line != null) {
                st = st + line;
            }
        }
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray)parser.parse(st);
        for (Object t:jsonar) {
            addProjekt(((JSONObject)t).get("projektname").toString());
            for (Object ts:(JSONArray)((JSONObject)t).get("Array")){
                Timestamp tsp;
                tsp = new Timestamp();
                tsp.setStemp(Long.parseLong(((JSONObject) ts).get("Time").toString()));
                tsp.setMonth(((JSONObject) ts).get("Month").toString());
                tsp.setMonth(((JSONObject) ts).get("Year").toString());
                tsp.setDay(((JSONObject) ts).get("Day").toString());
                p.addTimestamp(tsp);
            } ;
        }
    }

    private void addProjekt(String projektname){
        final FXMLLoader load2 = new FXMLLoader(getClass().getResource("projektview.fxml"));
        try {
            final Parent root = load2.load();
            Projektview g = load2.getController();
            g.set(h);
            p = g;
            h.addObject(g);
            g.setRoot(root);
            g.setProjekatname(projektname);
            workspace.getChildren().add(root);
        } catch (IOException e) {
            // TODO LogFile schreiben ergänzen.
        }
    }

    public void setWorkspace(JsonData workspace) {
        this.workspace = workspace;
    }
}
