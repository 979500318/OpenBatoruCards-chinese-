package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_Connection extends Card {

    public SPELL_G_Connection()
    {
        setImageSets("WDK03-020");

        setOriginalName("連鎖");
        setAltNames("レンサ Rensa");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対象のあなたの＜怪異＞のシグニ１体を場からトラッシュに置く。そうした場合、対象の対戦相手のパワー8000以上のシグニ１体をバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Connection");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your <<Apparition>> SIGNI, and put it into the trash. If you do, target 1 of your opponent's SIGNI with power 8000 or more, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "连锁");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对象的你的<<怪異>>精灵1只从场上放置到废弃区。这样做的场合，对象的对战对手的力量8000以上的精灵1只破坏。\n" +
                "$$2 [[能量填充1]]（你的牌组最上面的牌放置到能量区）\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);

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
                spell.addTarget(playerTargetCard(new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.APPARITION)).get());
                spell.addTarget(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(8000,0)).get());
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
