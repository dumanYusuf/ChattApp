ChatApp - Gerçek Zamanlı Mesajlaşma Uygulaması
ChatApp, kullanıcıların anlık mesajlar gönderebileceği ve alabileceği bir uygulamadır.
Firebase altyapısı kullanılarak geliştirilmiştir ve gerçek zamanlı veri güncellemeleri, push bildirimleri ve kullanıcı doğrulaması gibi özelliklere sahiptir.

Özellikler
Kullanıcı Doğrulama: Firebase Authentication ile kullanıcı kaydı ve girişi.

Gerçek Zamanlı Mesajlaşma: Firebase Firestore kullanarak anlık mesaj gönderme ve alma.

Push Bildirimleri: Yeni mesajlar için anlık push bildirimleri.

Kullanıcı Profili: Kullanıcılar profil fotoğrafı ekleyebilir.

Karanlık/Aydınlık Mod: Hem karanlık hem de aydınlık mod ile uyumlu mesajlaşma arayüzü.

Firebase Entegrasyonu: Firebase Firestore, Firebase Authentication ve Firebase Messaging ile backend hizmetleri sağlanır.

Kullanılan Teknolojiler
Firebase: Firestore, Authentication ve Cloud Messaging.

Kotlin: Android geliştirme için ana dil.

Jetpack Compose: Modern Android UI oluşturmak için kullanıldı.

Firebase Functions: Yeni mesaj gönderildiğinde push bildirimleri göndermek için kullanıldı.

Cloud Functions: Firestore'da yeni bir mesaj belgesi oluşturulduğunda tetiklenen fonksiyonlar.

Uygulama Yapısı
1. Doğrulama
Kullanıcılar e-posta ve şifre ile giriş yapabilir.

Firebase Authentication kullanılarak kullanıcı kaydı ve girişi yapılır.

2. Mesajlaşma
Kullanıcılar anlık olarak mesaj gönderebilir ve alabilir.


3. Push Bildirimleri
Firebase Cloud Messaging (FCM) kullanılarak yeni bir mesaj alındığında bildirim gönderilir.

Cloud Function, Firestore'daki yeni mesajları dinler ve bir kullanıcıya push bildirimi gönderir.

4. Kullanıcı Profili
Kullanıcılar, profil fotoğraflarını yükleyebilir ve düzenleyebilir.

Profil fotoğrafı Firebase Storage'a yüklenir ve URL Firestore'a kaydedilir.

5. Karanlık/Aydınlık Mod
Uygulama, karanlık ve aydınlık modları destekler. Seçilen tema bazında sohbet mesajları farklı şekilde tasarlanır.

Firebase Cloud Functions (Push Bildirimleri için)
Bir mesaj gönderildiğinde, Firebase Cloud Function, Firestore'daki yeni mesajları dinler ve alıcıya, göndericinin kullanıcı adı başlık olarak ve mesaj metni içerik olarak gönderilecek şekilde push bildirimi gönderir.

![image alt](https://github.com/dumanYusuf/ChattApp/blob/master/chatt1.png?raw=true)

