package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_BuffaloPhantomBeastDeity extends Card {
    
    public SIGNI_R3_BuffaloPhantomBeastDeity()
    {
        setImageSets("WXDi-P02-039");
        
        setOriginalName("幻獣神　バッファロー");
        setAltNames("ゲンジュウシンバッファロー Genjuushin Baffaroo");
        setDescription("jp",
                "@U：あなたの他の＜地獣＞のシグニ１体が場に出たとき、ターン終了時まで、そのシグニとこのシグニのパワーをそれぞれ＋4000する。\n" +
                "@U：あなたのアタックフェイズ開始時、ターン終了時まで、あなたのパワー20000以上のすべてのシグニは@>@U：このシグニがアタックしたとき、正面のパワー12000以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "Buffalo, Phantom Terra Beast God");
        setDescription("en",
                "@U: Whenever another <<Terra Beast>> SIGNI enters your field, this SIGNI and the SIGNI that has just entered your field both get +4000 power until end of turn.\n" +
                "@U: At the beginning of your attack phase, all SIGNI on your field with power 20000 or more gain@>@U: Whenever this SIGNI attacks, you may pay %R %X. If you do, vanish target SIGNI in front of this SIGNI with power 12000 or less.@@until end of turn."
        );
        
        setName("en_fan", "Buffalo, Phantom Beast Deity");
        setDescription("en_fan",
                "@U: Whenever 1 of your other <<Earth Beast>> SIGNI enters the field, until end of turn, that SIGNI and this SIGNI each get +4000 power.\n" +
                "@U: At the beginning of your attack phase, until end of turn, all of your SIGNI with power 20000 or more gain:" +
                "@>@U: Whenever this SIGNI attacks, target 1 SIGNI in front of it with power 12000 or less, and you may pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "幻兽神 水牛");
        setDescription("zh_simplified", 
                "@U :当你的其他的<<地獣>>精灵1只出场时，直到回合结束时为止，那只精灵和这只精灵的力量各+4000。\n" +
                "@U :你的攻击阶段开始时，直到回合结束时为止，你的力量20000以上的全部的精灵得到\n" +
                "@>@U :当这只精灵攻击时，正面的力量12000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ENTER, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller != getCardIndex() &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.EARTH_BEAST) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            DataTable<CardIndex> data = new DataTable<>();
            data.add(caller);
            data.add(getCardIndex());
            
            gainPower(data, 4000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            forEachSIGNIOnField(getOwner(), cardIndex -> {
                if(cardIndex.getIndexedInstance().getPower().getValue() >= 20000)
                {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
                }
            });
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndex = getAbility().getSourceCardIndex();
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000).match(cardIndex.getIndexedInstance().getOppositeSIGNI())).get();
            
            if(target != null && cardIndex.getIndexedInstance().payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
