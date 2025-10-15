package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_Acrobatics extends Card {

    public SPELL_G_Acrobatics()
    {
        setImageSets("WX25-P2-097");

        setOriginalName("曲芸");
        setAltNames("キョクゲイ Kyokugei");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのデッキの上からカードを３枚見る。その中から＜遊具＞のシグニ１枚をエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。\n" +
                "$$2あなたのエナゾーンから＜遊具＞のシグニ１枚を対象とし、それを場に出す。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Acrobatics");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Look at the top 3 cards of your deck. Put 1 <<Playground Equipment>> SIGNI from among them into the ener zone, and put the rest on the bottom of your deck in any order.\n" +
                "$$2 Target 1 <<Playground Equipment>> SIGNI from your ener zone, and put it onto the field." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "杂技");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的牌组上面看3张牌。从中把<<遊具>>精灵1张放置到能量区，剩下的任意顺序放置到牌组最下面。\n" +
                "$$2 从你的能量区把<<遊具>>精灵1张作为对象，将其出场。" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            spell.setModeChoice(1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            if(spell.getChosenModes() == 2) spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromEner().playable()));
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                look(3);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromLooked()).get();
                putInEner(cardIndex);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            } else {
                putOnField(spell.getTarget());
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromEner()).get();
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
