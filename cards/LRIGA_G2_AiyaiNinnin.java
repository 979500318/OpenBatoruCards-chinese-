package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class LRIGA_G2_AiyaiNinnin extends Card {

    public LRIGA_G2_AiyaiNinnin()
    {
        setImageSets("WXDi-P12-041");

        setOriginalName("アイヤイ　ニンニン");
        setAltNames("アイヤイニンニン");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@U $T1：あなたのシグニ１体がバトルによってシグニ１体をバニッシュしたとき、あなたのエナゾーンからシグニを１枚まで対象とし、それとそのあなたのシグニの場所を入れ替える。それの@E能力は発動しない。@@を得る。"
        );

        setName("en", "Aiyai, Nin - Nin");
        setDescription("en",
                "@E: This LRIG gains@>@U $T1: When a SIGNI on your field vanishes a SIGNI through battle, swap the position of up to one target SIGNI in your Ener Zone with that SIGNI on your field. The @E abilities of SIGNI put onto your field this way do not activate.@@until end of turn."
        );
        
        setName("en_fan", "Aiyai Ninnin");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U $T1: When 1 of your SIGNI banishes a SIGNI in battle, target up to 1 SIGNI from your ener zone and exchange its position with that SIGNI. Its @E abilities don't activate."
        );

		setName("zh_simplified", "艾娅伊 忍忍");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 当你的精灵1只因为战斗把精灵1只破坏时，从你的能量区把精灵1张最多作为对象，将其与那只你的精灵的场所交换。其的@E能力不能发动。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && !isOwnCard(caller) && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.MOVE).own().SIGNI().fromEner().playableAs(getEvent().getSourceCardIndex())).get();
            
            if(target != null)
            {
                putInEner(getEvent().getSourceCardIndex());
                putOnField(target, getEvent().getSourceCardIndex().getPreTransientLocation(), Enter.DONT_ACTIVATE);
            }
        }
    }
}
