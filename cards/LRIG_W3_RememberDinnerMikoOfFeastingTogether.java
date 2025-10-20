package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_RememberDinnerMikoOfFeastingTogether extends Card {

    public LRIG_W3_RememberDinnerMikoOfFeastingTogether()
    {
        setImageSets("WXDi-P10-005", "WXDi-P10-005U");

        setOriginalName("共宴の巫女　リメンバ・ディナー");
        setAltNames("キョウエンノミコリメンバディナー Kyouen no Miko Rimenba Dinaa");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、対戦相手のシグニ１体を対象とし、それをダウンし凍結する。\n" +
                "@A $T1 %W0：あなたのデッキの上からカードを３枚見る。その中からカード１枚を手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $G1 %W0：次の対戦相手のターン終了時まで、対戦相手のセンタールリグとすべてのシグニは能力を失う。"
        );

        setName("en", "Remember Dinner, Miko of Feasting");
        setDescription("en",
                "@U: Whenever this LRIG attacks, down target SIGNI on your opponent's field and freeze it.\n" +
                "@A $T1 %W0: Look at the top three cards of your deck. Add a card from among them to your hand and put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 %W0: Your opponent's Center LRIG and all SIGNI on your opponent's field lose their abilities until the end of your opponent's next end phase. "
        );
        
        setName("en_fan", "Remember Dinner, Miko of Feasting Together");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, target 1 of your opponent's SIGNI, and down and freeze it.\n" +
                "@A $T1 %W0: Look at the top 3 cards of your deck. Add 1 card from among them to your hand and put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 %W0: Until the end of your opponent's next turn, your opponent's center LRIG and all of their SIGNI lose their abilities."
        );

		setName("zh_simplified", "共宴的巫女 忆·晚餐");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，对战对手的精灵1只作为对象，将其横置并冻结。\n" +
                "@A $T1 %W0:从你的牌组上面看3张牌。从中把1张牌加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $G1 %W0:直到下一个对战对手的回合结束时为止，对战对手的核心分身和全部的精灵的能力失去。（这个能力使用后出场的分身和精灵不受这个效果影响）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
        }
        
        private void onActionEff1()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onActionEff2()
        {
            disableAllAbilities(getLRIG(getOpponent()), AbilityGain.ALLOW, ChronoDuration.nextTurnEnd(getOpponent()));
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
