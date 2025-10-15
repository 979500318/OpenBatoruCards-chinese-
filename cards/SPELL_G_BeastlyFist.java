package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_BeastlyFist extends Card {

    public SPELL_G_BeastlyFist()
    {
        setImageSets("WX24-P1-078");

        setOriginalName("獣拳");
        setAltNames("ジュウケン Juuken");
        setDescription("jp",
                "あなたの＜地獣＞のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000し、それは@>@U：このシグニがアタックしたとき、パワーがこのシグニのパワーの半分以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。@@を得る。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Beastly Fist");
        setDescription("en",
                "Target 1 of your <<Earth Beast>> SIGNI, and until end of turn, it gets +5000 power and:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power equal to or less than half this SIGNI's, and banish it.@@" +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "兽拳");
        setDescription("zh_simplified", 
                "你的<<地獣>>精灵1只作为对象，直到回合结束时为止，其的力量+5000，其得到\n" +
                "@>@U :当这只精灵攻击时，力量在这只精灵的力量的一半以下的对战对手的精灵1只作为对象，将其破坏。@@" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                gainPower(spell.getTarget(), 5000, ChronoDuration.turnEnd());
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex sourceCardIndex = getAbility().getSourceCardIndex();
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,sourceCardIndex.getIndexedInstance().getPower().getValue()/2)).get();
            sourceCardIndex.getIndexedInstance().banish(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
