package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventDraw;

public final class SIGNI_B1_BenitoiteNaturalStone extends Card {
    
    public SIGNI_B1_BenitoiteNaturalStone()
    {
        setImageSets("WXDi-P05-062");
        
        setOriginalName("羅石　ベニトアイト");
        setAltNames("ラセキベニトアイト Raseki Benitoaito");
        setDescription("jp",
                "@U $T1：ドローフェイズ以外であなたがカードを１枚引いたとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Benitoite, Natural Crystal");
        setDescription("en",
                "@U $T1: When you draw a card outside of your draw phase, you may discard a card. If you do, draw a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Benitoite, Natural Stone");
        setDescription("en_fan",
                "@U $T1: When you draw a card other than during your draw phase, you may discard 1 card from your hand. If you do, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "罗石 蓝锥石");
        setDescription("zh_simplified", 
                "@U $T1 :当在抽牌阶段以外你抽1张牌时，可以把手牌1张舍弃。这样做的场合，抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.DRAW, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && getCurrentPhase() != GamePhase.DRAW ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
