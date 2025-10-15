package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_K2_LilithWickedDevil extends Card {
    
    public SIGNI_K2_LilithWickedDevil()
    {
        setImageSets("WXDi-P07-094");
        
        setOriginalName("凶魔　リリス");
        setAltNames("キョウマリリス Kyouma Ririsu");
        setDescription("jp",
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。\n" +
                "$$2対戦相手のシグニ１体を対象とし、#Cを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Lilith, Doomed Evil");
        setDescription("en",
                "~#Choose one --\n$$1 Target SIGNI on your opponent's field gets -5000 power until end of turn.\n$$2 You may pay #C. If you do, target SIGNI on your opponent's field gets -12000 power until end of turn."
        );
        
        setName("en_fan", "Lilith, Wicked Devil");
        setDescription("en_fan",
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and you may pay #C. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "凶魔 莉莉丝");
        setDescription("zh_simplified", 
                "~#以下选1种。#C。这样做的场合，直到回合结束时为止，其的力量-12000。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n" +
                "$$2 对战对手的精灵1只作为对象，可以支付\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -5000, ChronoDuration.turnEnd());
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                if(target != null && payAll(new CoinCost(1))) gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
