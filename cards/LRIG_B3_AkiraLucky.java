package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_AkiraLucky extends Card {

    public LRIG_B3_AkiraLucky()
    {
        setImageSets("WX24-P2-022", "WX24-P2-022U");

        setOriginalName("あきら☆らっきー");
        setAltNames("アキララッキー Akira Rakkii");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、あなたの＜遊具＞のシグニ１体を場からデッキの一番下に置いてもよい。そうした場合、カードを１枚引き、対戦相手が手札を３枚捨てないかぎり、対戦相手にダメージを与える。\n" +
                "@A $G1 @[@|ぶっとばす！|@]@ %B0：あなたの手札が対戦相手より多い場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。あなたの手札が対戦相手より少ない場合、対戦相手は手札を３枚捨てる。"
        );

        setName("en", "Akira☆Lucky");
        setDescription("en",
                "@U: Whenever this LRIG attacks, you may put 1 <<Playground Equipment>> SIGNI from your field on the bottom of your deck. If you do, draw 1 card, and damage your opponent unless they discard 3 cards from their hand.\n" +
                "@A $G1 @[@|I'll Crush You!|@]@ %B0: If there are more cards in your hand than in your opponent's, target 1 of your opponent's SIGNI, and banish it. If there are less cards in your hand than in your opponent's, your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "晶☆幸运");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，可以把你的<<遊具>>精灵1只从场上放置到牌组最下面。这样做的场合，抽1张牌，如果对战对手不把手牌3张舍弃，那么给予对战对手伤害。\n" +
                "@A $G1 猛打狠揍！%B0:你的手牌比对战对手多的场合，对战对手的精灵1只作为对象，将其破坏。你的手牌比对战对手少的场合，对战对手把手牌3张舍弃。\n"
        );

        setLRIGType(CardLRIGType.AKIRA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("I'll Crush You!");
        }

        private void onAutoEff()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT)).get();
            if(returnToDeck(cardIndex, DeckPosition.BOTTOM))
            {
                draw(1);
                
                if(discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
                {
                    damage(getOpponent());
                }
            }
        }
        
        private void onActionEff()
        {
            if(getHandCount(getOwner()) > getHandCount(getOpponent()))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                banish(target);
            } else if(getHandCount(getOwner()) < getHandCount(getOpponent()))
            {
                discard(getOpponent(), 3);
            }
        }
    }
}
