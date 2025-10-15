package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_InsideDevils extends Card {

    public SPELL_K_InsideDevils()
    {
        setImageSets("WXDi-P08-081");

        setOriginalName("インサイド・デビルズ");
        setAltNames("インサイドデビルズ Insaido Debirizu");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、あなたの＜悪魔＞のシグニ１体を場からトラッシュに置く。そうした場合、ターン終了時まで、それのパワーを－10000する。\n" +
                "$$2あなたのトラッシュから＜悪魔＞のシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Inside Devils");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Put a <<Demon>> SIGNI on your field into its owner's trash. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n" +
                "$$2 Add target <<Demon>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Inside Devils");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and put 1 of your <<Devil>> SIGNI from the field into the trash. If you do, until end of turn, it gets --10000 power.\n" +
                "$$2 Target 1 <<Devil>> SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "负面·人格");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，你的<<悪魔>>精灵1只从场上放置到废弃区。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "$$2 从你的废弃区把<<悪魔>>精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(spell.getChosenModes() == 1 ? new TargetFilter(TargetHint.MINUS).OP().SIGNI() :
                                                                            new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardConst.CardSIGNIClass.DEVIL).fromTrash()));
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardConst.CardSIGNIClass.DEVIL)).get();
                
                if(trash(cardIndex))
                {
                    gainPower(spell.getTarget(), -10000, ChronoDuration.turnEnd());
                }
            } else {
                addToHand(spell.getTarget());
            }
        }
    }
}
