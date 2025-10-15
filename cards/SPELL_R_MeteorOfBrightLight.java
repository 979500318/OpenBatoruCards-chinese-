package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_MeteorOfBrightLight extends Card {

    public SPELL_R_MeteorOfBrightLight()
    {
        setImageSets("WDK01-020");

        setOriginalName("光明の流星");
        setAltNames("コウミョウノリュウセイ Koumyou no Ryuusei");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対象のあなたの＜乗機＞のシグニ１体を場からトラッシュに置く。そうした場合、対象の対戦相手のパワー12000以下のシグニ１体をバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Meteor of Bright Light");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your <<Riding Machine>> SIGNI, and put it into the trash. If you do, target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "光明的流星");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对象的你的<<乗機>>精灵1只从场上放置到废弃区。这样做的场合，对象的对战对手的力量12000以下的精灵1只破坏。\n" +
                "$$2 [[能量填充1]]（你的牌组最上面的牌放置到能量区）\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SPELL);
        setColor(CardColor.RED);

        setPlayFormat(PlayFormat.KEY);
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
            if(spell.getChosenModes() == 1)
            {
                spell.addTarget(playerTargetCard(new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE)).get());
                spell.addTarget(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get());
            }
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                if(trash(spell.getTarget(0)))
                {
                    banish(spell.getTarget(1));
                }
            } else {
                enerCharge(1);
            }
        }
    }
}
