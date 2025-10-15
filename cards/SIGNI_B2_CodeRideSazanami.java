package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_B2_CodeRideSazanami extends Card {

    public SIGNI_B2_CodeRideSazanami()
    {
        setImageSets("WXDi-P09-067");

        setOriginalName("コードライド　サザナミ");
        setAltNames("コードライドサザナミ Koodo Raido Sazanami");
        setDescription("jp",
                "@U：このシグニがライズされたとき、対戦相手の手札を見る。\n" +
                "@A #D：カードを２枚引き、手札を２枚捨てる。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Sazanami, Code: Ride");
        setDescription("en",
                "@U: When this SIGNI is risen, look at your opponent's hand.\n" +
                "@A #D: Draw two cards and discard two cards." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code Ride Sazanami");
        setDescription("en_fan",
                "@U: When you rise on this SIGNI, look at your opponent's hand.\n" +
                "@A #D: Draw 2 cards, and discard 2 cards from your hand." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "骑乘代号 涟号护卫舰");
        setDescription("zh_simplified", 
                "@U :当这只精灵被升阶时，看对战对手的手牌。\n" +
                "@A #D:抽2张牌，手牌2张舍弃。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.RISE, this::onAutoEff);
            
            registerActionAbility(new DownCost(), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            addToHand(getCardsInRevealed(getOpponent()));
        }
        
        private void onActionEff()
        {
            draw(2);
            discard(2);
        }

        private void onLifeBurstEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(cardIndex != null)
            {
                reveal(cardIndex);
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
