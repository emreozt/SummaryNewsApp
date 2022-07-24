package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.ModelClass;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<ModelClass> modelClassArrayList;

    public static String URL;
    public ArrayList liste = new ArrayList();


    public Adapter(Context mContext, ArrayList<ModelClass> modelClassArrayList) { //fragmentlardan aricle gibi verilere ulaşmak için
        this.context = mContext;
        this.modelClassArrayList = modelClassArrayList;
    }

    String aa="";
    String a= "";
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,null,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) { //verileri iteme veya position a göre ayarlamak için



        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,webView.class);
                intent.putExtra("url",modelClassArrayList.get(position).getUrl());
                context.startActivity(intent);

            }

        }

        );

        //holder.mUrl.setText(modelClassArrayList.get(position).getUrl());
        //URL= holder.mUrl.getText().toString();
        Glide.with(context).load(modelClassArrayList.get(position).getUrlToImage()).into(holder.imageView);
        URL= modelClassArrayList.get(position).getUrl();

        System.out.println(URL);


        new VeriGetir().execute();

        holder.mTime.setText("Yayınlanma Tarihi: "+modelClassArrayList.get(position).getPublishedAt());
        holder.mAuthor.setText(modelClassArrayList.get(position).getAuthor());
        holder.mHeading.setText(modelClassArrayList.get(position).getTitle());

        while(true){//asnyc bitmesini bekledik
            if(!a.isEmpty()){
                ozet nesne = new ozet(a);
                String oz = nesne.summarize(4);

                System.out.println("SUMMRY:   " + oz);
                if (oz.contains("Okurlarımız")|| (oz.contains("UYARI:")|| (oz.contains("Hürriyet'in internet")|| (oz.contains("kaynak gösterilmeden"))))) {
                    holder.mContent.setText(modelClassArrayList.get(position).getDescription());

                }
                else {
                    holder.mContent.setText(oz);
                }
                a = "";
                break;
            }
            else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHeading,mContent, mAuthor, mTime,mUrl;

        CardView cardView;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeading = itemView.findViewById(R.id.mainHeading);
            mContent = itemView.findViewById(R.id.content);
            mAuthor = itemView.findViewById(R.id.author);
            mTime = itemView.findViewById(R.id.time);
            //mUrl = itemView.findViewById(R.id.url);


            cardView = itemView.findViewById(R.id.cardView);

            imageView = itemView.findViewById(R.id.imageView);



        }
    }





    private class VeriGetir extends AsyncTask<Void, Void, Void>{
        public ArrayList liste = new ArrayList();




        @Override
        public Void doInBackground(Void... voids) {

            //if (URL.contains("www.youtube.com"))

            if (URL.contains("www.youtube.com")){
                System.out.println("Youtube haberi özetlenemez");

            }
            else {
                try {
                    Document doc = Jsoup.connect(URL).timeout(30 * 1000).get();

                    Elements content = doc.select("p");

                    for (int i = 0; i < content.size(); i++) {

                        liste.add(content.get(i).text());
                    }
                    for (int i = 0; i < liste.size(); i++) {

                        a = a + liste.get(i).toString();
                        a = a + " ";

                    }
                    System.out.println("sonuc " + a);


                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            return null;
        }
    }

    public class ozet {

        private String fullText;
        private HashMap<String, Integer> wordFrequency = new HashMap<>();
        private HashMap<Integer, Integer> sentenceScores = new HashMap<>();
        private String[] words;
        private ArrayList<String> sentences = new ArrayList<>();

        private String[] stopWords = {"ama",
                "amma",
                "anca",
                "ancak",
                "belki",
                "çünkü",
                "dahi",
                "eğer",
                "emme",
                "fakat",
                "gah",
                "gerek",
                "hakeza",
                "halbuki",
                "hatta",
                "hele",
                "hem",
                "hoş",
                "ile",
                "ile",
                "imdi",
                "ister",
                "kah",
                "keşke",
                "keza",
                "kezalik",
                "kim",
                "lakin",
                "madem",
                "mademki",
                "mamafih",
                "meğer",
                "meğerki",
                "meğerse",
                "netekim",
                "neyse",
                "nitekim",
                "oysa",
                "oysaki",
                "şayet",
                "velev",
                "velhasıl",
                "velhasılıkelam",
                "veya",
                "veyahut",
                "yahut",
                "yalnız",
                "yani",
                "yok",
                "yoksa",
                "zira",
                "acaba",
                "acep",
                "açıkça",
                "açıkçası",
                "adamakıllı",
                "adeta",
                "bazen",
                "bazı",
                "bilcümle",
                "binaen",
                "binaenaleyh",
                "bir",
                "biraz",
                "birazdan",
                "birden",
                "birden",
                "birdenbire",
                "birice",
                "birlikte",
                "bitevi",
                "biteviye",
                "bittabi",
                "bizatihi",
                "bizce",
                "bizcileyin",
                "bizden",
                "bizzat",
                "boşuna",
                "böyle",
                "böylece",
                "böylecene",
                "böylelikle",
                "böylemesine",
                "böylesine",
                "buracıkta",
                "burada",
                "buradan",
                "büsbütün",
                "çabuk",
                "çabukça",
                "çeşitli",
                "çoğu",
                "çoğun",
                "çoğunca",
                "çoğunlukla",
                "çok",
                "çokça",
                "çokluk",
                "çoklukla",
                "cuk",
                "daha",
                "dahil",
                "dahilen",
                "daima",
                "demin",
                "demincek",
                "deminden",
                "derakap",
                "derhal",
                "derken",
                "diye",
                "elbet",
                "elbette",
                "enikonu",
                "epey",
                "epeyce",
                "epeyi",
                "esasen",
                "esnasında",
                "etraflı",
                "etraflıca",
                "evleviyetle",
                "evvel",
                "evvela",
                "evvelce",
                "evvelden",
                "evvelemirde",
                "evveli",
                "gayet",
                "gayetle",
                "gayri",
                "gayrı",
                "geçende",
                "geçenlerde",
                "gene",
                "gerçi",
                "gibi",
                "gibilerden",
                "gibisinden",
                "gine",
                "halen",
                "halihazırda",
                "haliyle",
                "handiyse",
                "hani",
                "hasılı",
                "hulasaten",
                "iken",
                "illa",
                "illaki",
                "itibarıyla",
                "iyice",
                "iyicene",
                "kala",
                "kez",
                "kısaca",
                "külliyen",
                "lütfen",
                "nasıl",
                "nasılsa",
                "nazaran",
                "neden",
                "nedeniyle",
                "nedense",
                "nerde",
                "nerden",
                "nerdeyse",
                "nerede",
                "nereden",
                "neredeyse",
                "nereye",
                "neye",
                "neyi",
                "nice",
                "niçin",
                "nihayet",
                "nihayetinde",
                "niye",
                "oldu",
                "oldukça",
                "olur",
                "onca",
                "önce",
                "önceden",
                "önceleri",
                "öncelikle",
                "onculayın",
                "ondan",
                "oracık",
                "oracıkta",
                "orada",
                "oradan",
                "oranca",
                "oranla",
                "oraya",
                "öyle",
                "öylece",
                "öylelikle",
                "öylemesine",
                "pek",
                "pekala",
                "pekçe",
                "peki",
                "peyderpey",
                "sadece",
                "sahi",
                "sahiden",
                "sanki",
                "sonra",
                "sonradan",
                "sonraları",
                "sonunda",
                "şöyle",
                "şuncacık",
                "şuracıkta",
                "tabii",
                "tam",
                "tamam",
                "tamamen",
                "tamamıyla",
                "tek",
                "vasıtasıyla",
                "yakinen",
                "yakında",
                "yakından",
                "yakınlarda",
                "yalnız",
                "yalnızca",
                "yeniden",
                "yenilerde",
                "yine",
                "yok",
                "yoluyla",
                "yüzünden",
                "zaten",
                "zati",
                "ait",
                "bari",
                "beri",
                "bile",
                "değin",
                "dek",
                "denli",
                "doğru",
                "dolayı",
                "dolayısıyla",
                "gelgelelim",
                "gibi",
                "gırla",
                "göre",
                "hasebiyle",
                "için",
                "ila",
                "ile",
                "ilen",
                "indinde",
                "inen",
                "kadar",
                "kaffesi",
                "karşın",
                "kelli",
                "Leh",
                "maada",
                "mebni",
                "naşi",
                "rağmen",
                "üzere",
                "zarfında",
                "öbür",
                "bana",
                "başkası",
                "ben",
                "beriki",
                "birbiri",
                "birçoğu",
                "biri",
                "birileri",
                "birisi",
                "birkaçı",
                "biz",
                "bizimki",
                "buna",
                "bunda",
                "bundan",
                "bunlar",
                "bunu",
                "bunun",
                "burası",
                "çoğu",
                "çoğu",
                "çokları",
                "çoklarınca",
                "cümlesi",
                "değil",
                "diğeri",
                "filanca",
                "hangisi",
                "hepsi",
                "hiçbiri",
                "iş",
                "kaçı",
                "kaynak",
                "kendi",
                "kim",
                "kimi",
                "kimisi",
                "kimse",
                "kimse",
                "kimsecik",
                "kimsecikler",
                "nere",
                "neresi",
                "öbürkü",
                "öbürü",
                "ona",
                "onda",
                "ondan",
                "onlar",
                "onu",
                "onun",
                "öteki",
                "ötekisi",
                "öz",
                "sana",
                "sen",
                "siz",
                "şuna",
                "şunda",
                "şundan",
                "şunlar",
                "şunu",
                "şunun",
                "şura",
                "şuracık",
                "şurası"};

        public ozet(String fullText) {
            this.fullText = fullText;
        }


        public String summarize(int n) {
            //  kelimeleri fulltextten alır ve sıklığı sayar
            getWords();
            // metni cümlelere böler
            parseSentences();
            // her cümle için puanı hesaplanır (her kelime bir değer olarak frekansını ve uzunluğunu ekler)
            evaluateSentences();
            // en yüksek puana sahip "x" cümle sayısını döndür
            // showSummary(n);

            return getSummary(n);


        }

        private void getWords() {
            // belgedeki her kelimenin sıklığı sayılır (stop word yok sayılarak)
            words = fullText.split("\\W+");   //  "\\W , .,!* vb. gibi olmayan herhangi bir karakterde bölmek anlamına gelir.

            // stop words ile karşılaştırmak için tüm kelimeleri küçük harfe çevir
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].toLowerCase();
            }

            for (int i = 0; i < words.length; i++) {
                String currentWord = words[i];

                if (Arrays.asList(stopWords).contains(currentWord)) {
                    i++; // stop wordleri yok sayar
                } else {
                    int count = wordFrequency.containsKey(currentWord) ? wordFrequency.get(currentWord) : 0;
                    wordFrequency.put(currentWord, count + 1); // kelimenin sıklığını arttırır
                }
            }
        }

        private void parseSentences() {
            String[] ignoreWords = {"Dr", "www", "tr","gov","com","Prof",};
            String currentSentence;
            int currentChar = 0;
            int previousStop = 0;
            while (currentChar < fullText.length() - 1) {
                if (fullText.charAt(currentChar) == '?' || fullText.charAt(currentChar) == '!') {
                    // cümlenin sonu
                    currentSentence = fullText.substring(previousStop, currentChar + 1);
                    sentences.add(currentSentence);
                    currentChar++;
                    previousStop = currentChar;
                }// Bir period gördüğümüzde cümlenin bitip bitmediğini kontrol eder
                else if (fullText.charAt(currentChar) == '.') {
                    if (currentChar - previousStop <= 2) {
                        currentChar++;
                    }
                    else if (currentChar > 2) {
                        String twoLetterAbbrev = fullText.substring(currentChar - 2, currentChar);
                        String threeLetterAbbrev = fullText.substring(currentChar - 3, currentChar);

                        // ignore words like "Dr", "www", "tr","gov", Etc
                        if (Arrays.asList(ignoreWords).contains(twoLetterAbbrev) ||
                                Arrays.asList(ignoreWords).contains(threeLetterAbbrev)) {
                            currentChar++;
                        }// end of sentence
                        else {
                            currentSentence = fullText.substring(previousStop, currentChar + 1);
                            sentences.add(currentSentence);
                            currentChar++;
                            previousStop = currentChar;
                        }
                    }
                }
                currentChar++;
            }
            System.out.println();
            System.out.println("Number of Sentences: " + sentences.size());
        }

        private void evaluateSentences() {
            // calculate the score for each sentence (each word adds its frequency and length as a value)

            // 1. cümleyi al
            int sentenceCount = 0;
            for (String s: sentences) {
                int sentenceScore = 0;
                // 2. her bir cümle için değer hesapla
                String[] wordsInSentence = s.split(" ");
                for (String word: wordsInSentence) {
                    if (wordFrequency.get(word) != null) {
                        int value = wordFrequency.get(word);
                        value += word.length();
                        sentenceScore += value;
                    }

                }

                // 3. değerleri kaydet
                sentenceScores.put(sentenceCount, sentenceScore);
                sentenceCount++;
            }
        }

//        private void showSummary(int n) {// kullanılmıyor
//            // 1. Get the highest values in sentenceScores
//            int[] higest = findhighest(n);
//            // 2. Print those sentences in order
//            for (int x: higest) {
//
//
//                sentences.get(x);
//
//                for (int i = 0; i<n-1; i++){
//
//                    aa = aa + sentences.get(x);
//                }
//            }
//            System.out.println("özett "+aa);
//        }

        private String getSummary(int n) {
            // 1. sentenceScoresdan en yüksek değeri al
            int[] higest = findhighest(n);

            String summary = "";
            // 2. Bu cümleleri sırayla yazdır
            for (int x: higest) {
                sentences.get(x);
                // her bir cümleyi summary içerisine atarak tek bir değişkende tuttuk
                for (int i = 0; i<n-1; i++){

                    summary += sentences.get(x);
                }
            }
            //System.out.println("sum "+ summary);

            return summary;
        }

        private int[] findhighest(int n) {
            int[] topKeys = new int[n];
            List<Integer> values = new ArrayList<>(sentenceScores.values());
            Collections.sort(values, Collections.reverseOrder());
            List<Integer> topN = values.subList(0, n);
            List<Integer> keys = new ArrayList<>(sentenceScores.keySet());

            for (int key: keys) {
                int currentScore = sentenceScores.get(key);
                if (topN.contains(currentScore)) {
                    // Find the index
                    for (int i = 0; i < n; i++) {
                        if (topN.get(i) == currentScore) {
                            topKeys[i] = key;
                        }
                    }
                }
            }
            return topKeys;
        }
    }


}