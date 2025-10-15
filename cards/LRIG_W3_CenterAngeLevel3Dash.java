package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CollaboLiverCost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_CenterAngeLevel3Dash extends Card {

    public LRIG_W3_CenterAngeLevel3Dash()
    {
        setImageSets("WXDi-CP01-006");

        setOriginalName("【センター】アンジュ　レベル３'");
        setAltNames("センターアンジュレベルサンダッシュ Sentaa Anju Reberu San Dasshu Center Ange 3 Dash");
        setDescription("jp",
                "@E：コラボライバー２人を呼ぶ。\n" +
                "@A %X @[コラボライバー１人とコラボする]@：対戦相手のパワー12000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@A $G1 @[@|＃賢者の時間|@]@ %W0：ターン終了時まで、このルリグは@>@U $T1：このルリグがアタックしたとき、対戦相手が%X %X %X %Xを支払うか#Gを持つカードを１枚捨てないかぎり、対戦相手にダメージを与える。@@を得る。"
        );

        setName("en", "[Center] Ange, Level 3'");
        setDescription("en",
                "@E: Invite two Collab Livers.\n@A %X @[Collaborate with a Collab Liver]@: Return target SIGNI on your opponent's field with power 12000 or less to its owner's hand.\n@A $G1 #``Sage'sTime %W0: This LRIG gains@>@U $T1: When this LRIG attacks, it deals damage to your opponent unless they pay %X %X %X %X or discard a card with a #G.@@until end of turn."
        );
        
        setName("en_fan", "[Center] Ange Level 3'");
        setDescription("en_fan",
                "@E: Invite 2 CollaboLivers.\n" +
                "@A %X @[Collab with 1 CollaboLiver]@: Target 1 of your opponent's SIGNI with power 12000 or less, and return it to their hand.\n" +
                "@A $G1 @[@|#WingedOne'sTime|@]@ %W0: Until end of turn, this LRIG gains:" +
                "@>@U $T1: When this LRIG attacks, damage your opponent unless they discard 1 card with #G @[Guard]@ or pay %X %X %X %X."
        );

		setName("zh_simplified", "【核心】安洁 等级3'");
        setDescription("zh_simplified", 
                "@E :呼唤联动主播2人。\n" +
                "@A %X与联动主播1人联动:对战对手的力量12000以下的精灵1只作为对象，将其返回手牌。\n" +
                "@A $G1 #贤者的时间%W0:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 :当这只分身攻击时，如果对战对手不把%X %X %X %X支付或把持有#G的牌1张舍弃，那么给予对战对手伤害。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new CollaboLiverCost(1)), this::onActionEff1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("#Winged One's Time");
        }
        
        private void onEnterEff()
        {
            inviteCollaboLivers(2);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,12000)).get();
            addToHand(target);
        }

        private void onActionEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            if(!pay(getOpponent(), new DiscardCost(0,1, new TargetFilter().withState(CardStateFlag.CAN_GUARD)), new EnerCost(Cost.colorless(4))))
            {
                damage(getOpponent());
            }
        }
    }
}
