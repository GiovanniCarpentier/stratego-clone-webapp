let audio;
function checkEasterEggs(roomid){
    if (roomid==="69"){
        showAchievement();
    }
    if (roomid==="pewdiepie"){
        audio = new Audio('./assets/media/hiddenExtra/pewdiepie.mp3');
        audio.play();
    }
}
function playWarCry(){
    if (countWarcry===99){
        audio = new Audio('./assets/media/achievements/suprise.mp3');
        document.querySelector("#noLife2").style.display = 'block';
    }else if (countWarcry===9){
        audio = new Audio('./assets/media/achievements/wow.mp3');
        document.querySelector("#warCryAchievement").style.display = 'block';
    }else{
        audio = new Audio('./assets/media/achievements/roblox-death-sound_1.mp3');
    }
    audio.play();
    countWarcry+=1;
}
