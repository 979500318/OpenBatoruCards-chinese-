package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_MikanShiratamaPriParaIdol extends Card {

    public SIGNI_R2_MikanShiratamaPriParaIdol()
    {
        setImageSets("WXDi-P10-054");

        setOriginalName("プリパラアイドル　白玉みかん");
        setAltNames("プリパラアイドルシラタマミカン Puripara Aidoru Shiratama Mikan");
        setDescription("jp",
                "@U $T2：あなたのシグニ１体がアタックしたとき、ターン終了時まで、そのシグニのパワーを＋2000する。そのシグニが＜プリパラ＞の場合、代わりにターン終了時まで、そのシグニのパワーを＋4000する。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Shiratama Mikan, Pripara Idol");
        setDescription("en",
                "@U $T2: Whenever a SIGNI on your field attacks, it gets +2000 power until end of turn. If it is a <<Pripara>> SIGNI, it gets +4000 power until end of turn instead." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Mikan Shiratama, PriPara Idol");
        setDescription("en_fan",
                "@U $T2: Whenever 1 of your SIGNI attacks, until end of turn, that SIGNI gets +2000 power. If it is a <<PriPara>> SIGNI, until end of turn, it gets +4000 power instead." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );

		setName("zh_simplified", "美妙天堂偶像 白玉蜜柑");
        setDescription("zh_simplified", 
                "@U $T2 :当你的精灵1只攻击时，直到回合结束时为止，那只精灵的力量+2000。那只精灵是<<プリパラ>>的场合，作为替代，直到回合结束时为止，那只精灵的力量+4000。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(caller, !caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PRIPARA) ? 2000 : 4000, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
