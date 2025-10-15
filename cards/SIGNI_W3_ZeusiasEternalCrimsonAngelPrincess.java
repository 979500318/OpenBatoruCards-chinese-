package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_ZeusiasEternalCrimsonAngelPrincess extends Card {

    public SIGNI_W3_ZeusiasEternalCrimsonAngelPrincess()
    {
        setImageSets("WXDi-P16-048");

        setOriginalName("夢限紅天姫　ゼウシアス");
        setAltNames("ムゲンコウテンキゼウシアス Mugen Koutenki Zeushiasu");
        setDescription("jp",
                "=T ＜夢限少女＞\n" +
                "^U：このシグニがアタックしたとき、以下の３つから２つまで選ぶ。\n" +
                "$$1次の対戦相手のターン終了時まで、このシグニは【シャドウ】を得る。\n" +
                "$$2対戦相手のパワー8000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。\n" +
                "$$3対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Zeusias, Mugen Crimson Angel Queen");
        setDescription("en",
                "=T <<MUGEN SHOJO>>\n^U: Whenever this SIGNI attacks, choose up to two of the following. \n$$1This SIGNI gains [[Shadow]] until the end of your opponent's next end phase. \n$$2You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 8000 or less.\n$$3Your opponent discards a card at random."
        );
        
        setName("en_fan", "Zeusias, Eternal Crimson Angel Princess");
        setDescription("en_fan",
                "=T <<Mugen Shoujo>>\n" +
                "^U: Whenever this SIGNI attacks, @[@|choose up to 2 of the following:|@]@\n" +
                "$$1 Until the end of your opponent's next turn, this SIGNI gains [[Shadow]].\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 8000 or less, and you may discard 1 card from your hand. If you do, banish it.\n" +
                "$$3 Choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "梦限红天姬 宙斯");
        setDescription("zh_simplified", 
                "=T<<夢限少女>>\n" +
                "^U:当这只精灵攻击时，从以下的3种选2种最多。\n" +
                "$$1 直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影]]。（这只精灵不会被对战对手作为对象）\n" +
                "$$2 对战对手的力量8000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n" +
                "$$3 不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            int modes = playerChoiceMode(0,2);
            if((modes & 1) != 0)
            {
                attachAbility(getCardIndex(), new StockAbilityShadow(), ChronoDuration.nextTurnEnd(getOpponent()));
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                
                if(target != null && discard(0,1).get() != null)
                {
                    banish(target);
                }
            }
            if((modes & 1<<2) != 0)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
