package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.game.FieldZone;

public final class SIGNI_G1_CodeMazeKawa21 extends Card {
    
    public SIGNI_G1_CodeMazeKawa21()
    {
        setImageSets("WXDi-P00-068");
        
        setOriginalName("コードメイズ　カワニイ");
        setAltNames("コードメイズカワニイ Koodo Meizu Kawanii");
        setDescription("jp",
                "@E：あなたのシグニ１体を対象とし、それをあなたの他のシグニゾーン１つに配置してもよい。そうした場合、ターン終了時まで、それのパワーを＋３０００する。" +
                "~#：対戦相手のシグニ１体を対象とし、%G %Gを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Kawanii, Code: Maze");
        setDescription("en",
                "@E: You may move target SIGNI on your field to a different SIGNI Zone. If you do, that SIGNI gets +3000 power until end of turn" +
                "~#You may pay %G %G. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code Maze Kawa 21");
        setDescription("en_fan",
                "@E: Target 1 of your SIGNI, and you may move it onto 1 of your other SIGNI zones. If you do, until end of turn, it gets +3000 power." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %G %G. If you do, banish it."
        );
        
		setName("zh_simplified", "迷宫代号 金泽21世纪美术馆");
        setDescription("zh_simplified", 
                "@E :你的精灵1只作为对象，可以将其往你的其他的精灵区1个配置。这样做的场合，直到回合结束时为止，其的力量+3000。" +
                "~#对战对手的精灵1只作为对象，可以支付%G %G。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MOVE).own().SIGNI()).get();
            
            if(cardIndex != null)
            {
                FieldZone fieldZone = playerTargetZone(0,1, new TargetFilter(TargetHint.MOVE).own().SIGNI().unoccupied()).get();
                
                if(fieldZone != null && moveToZone(cardIndex, fieldZone.getZoneLocation()))
                {
                    gainPower(cardIndex, 3000, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(cardIndex != null && payEner(Cost.color(CardColor.GREEN, 2)))
            {
                banish(cardIndex);
            }
        }
    }
}
