package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K1_TimerBombExplosiveNaturalStar extends Card {
    
    public SIGNI_K1_TimerBombExplosiveNaturalStar()
    {
        setImageSets("WXDi-P05-080");
        
        setOriginalName("羅爆星　タイマーボム");
        setAltNames("ラバクセイタイマーボム Rabakusei Taimaa Bomu");
        setDescription("jp",
                "@E %X %X：あなたのトラッシュから＜宇宙＞のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "Timer Bomb, Natural Erupting Planet");
        setDescription("en",
                "@E %X %X: Add target <<Cosmos>> SIGNI from your trash to your hand." +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Timer Bomb, Explosive Natural Star");
        setDescription("en_fan",
                "@E %X %X: Target 1 <<Space>> SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );
        
		setName("zh_simplified", "罗爆星 定时炸弹");
        setDescription("zh_simplified", 
                "@E %X %X:从你的废弃区把<<宇宙>>精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.SPACE).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
