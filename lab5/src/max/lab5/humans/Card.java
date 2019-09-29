package max.lab5.humans;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZonedDateTime;

public class Card extends Options implements Comparable{

    @Override
    public int compareTo(Object o) {
        return hashCode() - o.hashCode();
    }
    /**------------------------------------------------------------------------------------------------------------ */
    public class Photo {
        private Eyes eyes;
        private Hair hair;

        public Eyes getEyes(){ return eyes; }
        public Hair getHair(){ return hair; }

        private Photo(Eyes eyes, Hair hair){
                this.hair = hair;
                this.eyes = eyes;
        }

        private Photo(JSONObject json){
            this.eyes = Eyes.valueOf(json.getString("eyes"));
            this.hair = Hair.valueOf(json.getString("hair"));
        }

        public JSONObject getJson(){
            JSONObject json = new JSONObject();
            json.put("eyes", eyes.toString());
            json.put("hair", hair.toString());
            return json;
        }
    }
    /**------------------------------------------------------------------------------------------------------------ */
    private int cardWidth;
    private int cardHeight;
    private Photo photo;
    private ZonedDateTime date;
    private String owner;

    public Photo getPhoto(){
        return photo;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    /**------------------------------------------------------------------------------------------------------------ */
    public Card(int cardHeight, int cardWidth, Human h, String owner) {
        super(h.getName(), h.getStatus(), h.getHeight(), h.getHeadSize(), h.getNoseSize());
        photo = new Photo(h.getEyes(), h.getHair());
        this.cardHeight = cardHeight;
        this.cardWidth = cardWidth;
        this.date = ZonedDateTime.now();
        this.owner = owner;
    }

    public Card(JSONObject json){
        super(json.getString("name"),
                Status.valueOf(json.getString("status")),
                json.getDouble("height"), json.getDouble("headsize"),
        json.getDouble("nosesize"));
        this.cardHeight = json.getInt("cardheight");
        this.cardWidth = json.getInt("cardwidth");
        //this.date = ZonedDateTime.parse(json.getString("date"));
        this.date = ZonedDateTime.now();
        this.owner = json.getString("owner");
        try {
            this.photo = new Photo(new JSONObject(json.getString("photo")));
        }
        catch (JSONException e){

            this.photo = new Photo(json.getJSONObject("photo"));

        }
    }
    /**------------------------------------------------------------------------------------------------------------
     * @return*/

    public ZonedDateTime getDate(){ return date; }

    @Override
    public int hashCode(){
        return (int)(getHeight() * 7) + (int)(getHeadSize() * 11) + (int)(getNoseSize() * 13) + getName().hashCode() + getPhoto().getEyes().hashCode() + getPhoto().getHair().hashCode(); }
    @Override
    public String toString(){
        return "Имя: "+getName()+"\n"+"Статус: "+getStatus()+"\n"+"Рост: "+getHeight()+"\n"+"Размер головы: "+getHeadSize()+"\n"+"Размер носа: "+getNoseSize()+"\n"+"Цвет глаз: "+photo.eyes+"\n"+"Цвет волос: "+photo.hair+ "\n"+"Дата: "+getDate(); }
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;
        Card other = (Card)o;
        return other.hashCode() == this.hashCode();
    }

    public JSONObject getJson(){
        JSONObject json = new JSONObject();
        json.put("cardHeight", this.cardHeight);
        json.put("cardWidth", this.cardWidth);
        json.put("photo", photo.getJson());
        json.put("name", getName());
        json.put("status", getStatus().toString());
        json.put("height", getHeight());
        json.put("headsize", getHeadSize());
        json.put("nosesize", getNoseSize());
        json.put("date", getDate());
        json.put("owner", this.owner);
        return json;
    }

    public String getOwner(){
         return this.owner;
    }
}
