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

public final class SPELL_R_ExplosiveDragonImpact extends Card {

    public SPELL_R_ExplosiveDragonImpact()
    {
        setImageSets("WX24-P2-073");

        setOriginalName("爆竜の衝突");
        setAltNames("バクリュウノショウトツ Bakuryuu no Shoutotsu");
        setDescription("jp",
                "あなたの＜龍獣＞のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のパワー12000以下のシグニ１体を対象とし、対戦相手が%X %X %Xを支払わないかぎり、それをバニッシュする。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Explosive Dragon Impact");
        setDescription("en",
                "Target 1 of your <<Dragon Beast>> SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 12000 or less, and banish it unless your opponent pays %X %X %X.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "爆龙的冲突");
        setDescription("zh_simplified", 
                "你的<<龍獣>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵攻击时，对战对手的力量12000以下的精灵1只作为对象，如果对战对手不把%X %X %X:支付，那么将其破坏。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.DRAGON_BEAST)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            if(target != null && !getAbility().getSourceCardIndex().getIndexedInstance().payEner(getOpponent(), Cost.colorless(3)))
            {
                getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
