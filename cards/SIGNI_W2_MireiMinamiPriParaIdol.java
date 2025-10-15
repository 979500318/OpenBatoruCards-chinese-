package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W2_MireiMinamiPriParaIdol extends Card {

    public SIGNI_W2_MireiMinamiPriParaIdol()
    {
        setImageSets("WXDi-P10-048");

        setOriginalName("プリパラアイドル　南みれぃ");
        setAltNames("プリパラアイドルミナミミレィ Puripara Aidoru Minami Mirei");
        setDescription("jp",
                "@E %X：あなたのデッキの上からカードを５枚見る。その中から＜プリパラ＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Minami Mirei, Pripara Idol");
        setDescription("en",
                "@E %X: Look at the top five cards of your deck. Reveal a <<Pripara>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Mirei Minami, PriPara Idol");
        setDescription("en_fan",
                "@E %X: Look at the top 5 cards of your deck. Reveal 1 <<PriPara>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "美妙天堂偶像 南米蕾");
        setDescription("zh_simplified", 
                "@E %X:从你的牌组上面看5张牌。从中把<<プリパラ>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(2);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromLooked()).get();
            addToHand(cardIndex);

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
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
