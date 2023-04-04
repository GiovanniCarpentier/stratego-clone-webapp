let countWarcry=0;

function playWarCry() {
    let audio;
    if(countWarcry===99){
        audio = new Audio('./assets/media/achievements/suprise.mp3');
        document.querySelector("#noLife2").style.display = 'inline-block';
    }else if (countWarcry===9){
        audio = new Audio('./assets/media/achievements/wow.mp3');
        document.querySelector("#warCryAchievement").style.display = 'inline-block';
    }else{
        audio = new Audio('./assets/media/achievements/roblox-death-sound_1.mp3');
    }
    audio.play();
    countWarcry+=1;
}

function showAchievement(){
    document.querySelector("#nice").style.display ='inline-block';
    let audio;
    audio = new Audio('./assets/media/achievements/nice.mp3');
    audio.play();
}
