# TextToSpeech
https://developer.android.com/reference/android/speech/tts/TextToSpeech

OnInitListener，重寫onInit方法

SeekBars調整口頭文字的音調和速度
#
LANG_NOT_SUPPORTED
表示不支持該語言。

LANG_MISSING_DATA
表示語言資料遺失。
#
TextToSpeech(Context context, TextToSpeech.OnInitListener listener)

TextToSpeech類的構造函數，使用默認的TTS引擎。
#
.setLanguage(Locale.CHINA);

setLanguage(Locale loc)

設置文本到語音的語言。
#
.setPitch();

setPitch(float pitch)

設置TextToSpeech引擎的語音音調。
#
.setSpeechRate();

setSpeechRate(float speechRate)
設置語速。
#
.speak();
#
.addSpeech();

addSpeech(String text, String filename)

在文本字符串和聲音文件之間添加映射。
#
.setVoice();

setVoice(Voice voice)

設置文本到語音的語音。
#
.setOnUtteranceProgressListener();

setOnUtteranceProgressListener(UtteranceProgressListener listener)
  
設置偵聽器，該偵聽器將被通知與給定語音合成相關的各種事件。
#
.stop();

中斷當前講話（無論是播放還是渲染到文件中），並丟棄隊列中的其他講話。
#
.shutdown();

釋放TextToSpeech引擎使用的資源。
#
