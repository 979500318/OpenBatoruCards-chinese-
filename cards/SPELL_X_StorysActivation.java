package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass;
import open.batoru.data.CardDataSIGNIClass.CardSIGNIClassValue;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;

import java.util.List;

public final class SPELL_X_StorysActivation extends Card {

    public SPELL_X_StorysActivation()
    {
        setImageSets("WXDi-CP02-103");

        setOriginalName("物語の起動");
        setAltNames("モノガタリノキドウ Monogatari no Kidou");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1好きな生徒１人との絆を獲得する。\n" +
                "$$2あなたのデッキの上からカードを５枚見る。その中から＜ブルアカ＞のシグニを１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。\n\n" +
                "@C：このカードはすべての領域で＜ブルアカ＞として扱う。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1好きな生徒１人との絆を獲得する。\n" +
                "$$2あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Activating the Story");
        setDescription("en",
                "Choose one of the following.\n$$1Start a relationship with any one student. \n$$2Look at the top five cards of your deck. Reveal up to one <<Blue Archive>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order.\n\n@C: This card is treated as a <<Blue Archive>> in all zones." +
                "~#Choose one -- \n$$1Start a relationship with any one student. \n$$2Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Story's Activation");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Gain a bond with a student of your choice.\n" +
                "$$2 Look at the top 5 cards of your deck. Reveal up to 1 <<Blue Archive>> SIGNI from among them, add it to your hand, and put the rest on the bottom of your deck in any order.\n\n" +
                "@C: This card is treated as <<Blue Archive>> in all zones." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Gain a bond with a student of your choice.\n" +
                "$$2 Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "物语的起动");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 获得与任意学生1人的羁绊。（牌名1种宣言，其的[羁绊]能力变为有效）\n" +
                "$$2 从你的牌组上面看5张牌。从中把<<ブルアカ>>精灵1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@C :这张牌在全部的领域视作<<ブルアカ>>。" +
                "~#以下选1种。\n" +
                "$$1 获得与任意学生1人的羁绊。\n" +
                "$$2 从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setCost(Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEff);
            spell.setModeChoice(1);
            
            ConstantAbility cont = registerConstantAbility(new ModifiableBaseValueModifier<>(this::onConstEffModGetSample, () -> List.of(
                new CardSIGNIClassValue(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE)
            )));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private CardDataSIGNIClass onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getSIGNIClass();
        }
        
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                playerChoiceBond();
            } else {
                look(5);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked()).get();
                reveal(cardIndex);
                addToHand(cardIndex);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                playerChoiceBond();
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
                addToHand(target);
            }
        }
    }
}
