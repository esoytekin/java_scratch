# Sosyal Yazilim Case Study

Sosyal yazilim product ekibi gelistirmek istedigi yeni ozellikte, futbolculari
ve takimlari ozetleyen bir sayfa yapmak istiyor.  Oncelikle bu datayi
olusturmak icin, takim ve oyuncularin CRUD operasyonlarini iceren bir rest
api'ye ihtiyacimiz var.  Girilen datalari bir mikro servisten yayinlamak
istiyoruz.

! baslamadan once bir hatirlatma: Sizin belirleyeceginiz model ve datalar ile
uygulama yaga kaldirilmali.

## Senaryo 1

Takim ve futbolcu arsiv datasini olusturmak icin, insert update ve delete
isteklerini iceren REST api.

## Senaryo 2

Client, butun futbolcularii listeleyip ardindan secilen futbolcunun takimlarini
ikinci istekte alacak.

## Senaryo 3

Client butun takimlari listeleyip, ardindan takim ve yil parametreleri ile
sozlesmesi olan futbolculari isteyecek.

## Senaryo 4

Futbolcularin niteliklerini ve transfer ucretlerinin alindigi bir endpoint
tasarlayacagiz. Her takim sozlesme ucretini kendi para birimi uzerinden
hesaplayacak.  Transfer Ucreti = Tecurbe AY sayisi * 100.000/YASI Takim
Komisyonu, Transfer Ucreti'nin %10'u kadar olacak.  Sozlesme bedeli ise
Transfer Ucreti + Takim Komisyonu olarak hesaplanacak.

## Beklentiler

* Yaptigin projeyi Java8 kullanarak mikro servis mimarisine uygun yazarsan
  seviniriz.  
* Ayrica iliskisel veri tabani ile calisabilmeli ve Rest pratiklerine uygun
  olmali. 
* Unit testler olmazsa olmazimiz.  Swagger ve Postman toollarini kullanarak
  projeyi teslim edersen daha rahat test edebiliriz.
