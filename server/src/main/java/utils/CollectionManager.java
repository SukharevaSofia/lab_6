package utils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import data.*;
import serialize.AnswerSerialize;
import serialize.CommandSerialize;
import tools.DataInput;

/**
 * Класс, управляющий коллекцией
 */
public class CollectionManager {
    private static LinkedHashMap<Integer, MusicBand> data = new LinkedHashMap<Integer, MusicBand>();
    private LocalDate lastInitTime;
    private LocalDate lastSaveTime;
    private String readEnv;
    private String writeEnv;
    private DataInput dataInput;

    public CollectionManager(String readEnv, String writeEnv, DataInput dataInput){
        this.readEnv = readEnv;
        this.writeEnv = writeEnv;
        this.dataInput = dataInput;
        init();
    }


    public final void init() {
//        data = JsonManager.readCollection(readEnv);
    }

    public AnswerSerialize show() {
        if(data.size() > 0) {
            String output = data.entrySet().stream()
                    .map(entry -> entry.getValue().toString())
                    .collect(Collectors.joining("\n"));
            return new AnswerSerialize(output);
        }
        else return new AnswerSerialize("Коллекция пуста");
    }

    public final AnswerSerialize info(){

        try{
            if(lastSaveTime == null || lastInitTime == null) throw new NullPointerException();
            return new AnswerSerialize("Время последней инициализации : " +  lastInitTime + "\n" +
                    "Время последнего сохранения : " + lastSaveTime + "\n" +
                    "Тип : " + data.getClass() + "\n" +
                    "Количество элементов : " + data.size() + "\n");
        }
        catch (NullPointerException e){

            try{
                if(lastSaveTime == null || lastInitTime == null) throw new NullPointerException();
                return new AnswerSerialize("Время последней инициализации : " +  lastInitTime + "\n" +
                        "Время последнего сохранения коллекции не было сохранено\n" +
                        "Тип : " + data.getClass() + "\n" +
                        "Количество элементов : " + data.size() + "\n");
            }
            catch (NullPointerException e1){
                return new AnswerSerialize(
                        "Время последней инициализации : коллекция не найдена!\n" +
                                "Время последнего сохранения коллекции не было сохранено\n" +
                                "Тип : " + data.getClass() + "\n" +
                                "Количество элементов : " + data.size() + "\n")
                        ;

            }

        }
    }

    public AnswerSerialize insert(MusicBand musicBand) {
        data.put(musicBand.getId(), musicBand); //puts id + new band element into collection
        lastInitTime = LocalDate.now(); //updates init time
        return new AnswerSerialize( "Успешный ввод!");
    }

    public final AnswerSerialize removeById(String id) {
        try {
            int newId = Integer.parseInt(id);
            if (data.containsKey(newId)) {
                data.remove(newId);
                return new AnswerSerialize("Удаление прошло успешно!");
            } else
                return new AnswerSerialize("ID не найден!");
        } catch (NumberFormatException e) {
            return new AnswerSerialize("ID должен быть числом!");
        }
    }

    public AnswerSerialize clear() {
        data.clear();
        return new AnswerSerialize("Коллекция очищена");
    }

    public AnswerSerialize updateById(String id) {

        try {
            int newId = Integer.parseInt(id);
            if (data.containsKey(newId)) {
                data.remove(newId);
                data.put(newId, dataInput.askMusicBand());
                return new AnswerSerialize("Успешно обновлено!");
            } else
                return new AnswerSerialize("ID не найден!");

        } catch (NumberFormatException e) {
            return new AnswerSerialize("ID должен быть числом!");
        }
    }

    public AnswerSerialize save(){
        lastSaveTime = LocalDate.now();
//        return new AnswerSerialize(JsonManager.writeCollection(data, writeEnv));
        return new AnswerSerialize("");
    }


    public AnswerSerialize removeGreater(String element) {

        for (Map.Entry<Integer, MusicBand> map : data.entrySet()) {

            if (map.getValue().getName().compareTo(element) < 0) {
                data.remove(map.getKey());
            }
        }
        return new AnswerSerialize("Элементы удалены");
    }

    public AnswerSerialize replaceIfLower(String element){

        int cnt = 0;
        for (Map.Entry<Integer, MusicBand> map : data.entrySet()) {

            if (map.getValue().getName().compareTo(element) < 0) {
                map.getValue().setName(DataInput.askName());
                cnt++;
            }
        }

        if(cnt == 0){
            return new AnswerSerialize("Значений меньше, чем " + element + " не найдено!");
        }
        else if(cnt == 1) {
            return new AnswerSerialize("Значение успешно изменено!");
        }
        else return new AnswerSerialize("Значения успешно изменены!");

    }

    public AnswerSerialize removeGreaterKey(String key){

        try{
            int cnt = 0;
            int newKey = Integer.parseInt(key);
            for(int keys : data.keySet()){

                if(keys < newKey){
                    data.remove(keys);
                    cnt++;
                }
            }
            if(cnt == 0){
                return new AnswerSerialize("Значений больше, чем " + key + " не найдено!");
            }
            else if(cnt == 1) {
                return new AnswerSerialize("Значение успешно удалено!");
            }
            else return new AnswerSerialize("Значения успешно удалены!");
        }
        catch (NumberFormatException e){
            return new AnswerSerialize("Ключ должен являться числом!");
        }
    }

    public AnswerSerialize filterLessThanGenre(String genre){
        String outputOrder;
        LinkedList<MusicBand> musicBands = new LinkedList<MusicBand>();
        MusicGenre newGenre = MusicGenre.valueOf(genre.toUpperCase());
        try {
            for (Map.Entry<Integer, MusicBand> map : data.entrySet()) {

                if (map.getValue().getGenre().compareTo(newGenre) > 0) {
                    musicBands.add(map.getValue());
                }
            }
            outputOrder = Arrays.toString(new LinkedList[]{musicBands});
            return new AnswerSerialize(outputOrder);
        }
        catch(NumberFormatException e){
            return new AnswerSerialize("Аргумент должен быть названием жанра!");
        }
    }

    public AnswerSerialize countGreaterThanDescription(String description){
        try{
            String newDescription = description;
            int cnt = 0;
            for (Map.Entry<Integer, MusicBand> map : data.entrySet()) {

                if (map.getValue().getDescription().compareTo(description) < 0) {
                    cnt++;
                }
            }
            return new AnswerSerialize(cnt + "");
        }
        catch (NumberFormatException e){
            return new AnswerSerialize("Аргумент должен быть строкой!");
        }
    }

    public AnswerSerialize printDescendingParticipants(){
        String outputOrder = "";
        List<Integer> participantsOrder = new ArrayList<>();

        for (Map.Entry<Integer, MusicBand> map : data.entrySet()) {
                participantsOrder.add(map.getValue().getNumberOfParticipants());
        }
        participantsOrder.sort(Collections.reverseOrder());

        outputOrder = Arrays.toString(new List[]{participantsOrder});
        return new AnswerSerialize(outputOrder);
    }

    @Override
    public String toString() {
        return "CollectionManager []";
    }
}
