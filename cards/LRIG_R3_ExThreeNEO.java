package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.events.EventAttack;

public final class LRIG_R3_ExThreeNEO extends Card {

    public LRIG_R3_ExThreeNEO()
    {
        setImageSets("SPDi43-03", "SPDi43-03P");

        setOriginalName("エクス・スリーNEO");
        setAltNames("エクススリーネオ Ekusu Surii Neo");
        setDescription("jp",
                "@A %R0：あなたのシグニ１体を対象とし、このルリグの下からカード１枚をそれの【ソウル】にする。\n" +
                "@A $G1 @[@|バーテックス|@]@ @[ルリグデッキからアーツ１枚をルリグトラッシュに置く]@：対戦相手のライフクロス１枚をクラッシュする。ターン終了時まで、このルリグは@>@U：このルリグがアタックしたとき、そのアタック終了時、そのアタックによって対戦相手にダメージが与えられていなかった場合、%Rを支払ってもよい。そうした場合、このルリグをアップする。@@を得る。"
        );

        setName("en", "Ex Three NEO");
        setDescription("en",
                "@A %R0: Target 1 of your SIGNI, and attach 1 card under this LRIG to it as a [[Soul]].\n" +
                "@A $G1 @[@|Vertex|@]@ @[Put 1 ARTS from the LRIG deck into the LRIG trash]@: Crush 1 of your opponent's life cloth. Until end of turn, this LRIG gains:" +
                "@>@U: Whenever this LRIG attacks, at the end of the attack, if the attack did not damage your opponent, you may pay %R. If you do, up this LRIG."
        );

		setName("zh_simplified", "艾克斯·叁NEO");
        setDescription("zh_simplified", 
                "@A %R0:你的精灵1只作为对象，从这只分身的下面把1张牌作为其的[[灵魂]]。\n" +
                "@A $G1 顶点从分身牌组把必杀1张放置到分身废弃区:对战对手的生命护甲1张击溃。直到回合结束时为止，这只分身得到\n" +
                "@>@U :当这只分身攻击时，那次攻击结束时，没有因为那次攻击给予对战对手伤害的场合，可以支付%R。这样做的场合，这只分身竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new TrashCost(new TargetFilter().ARTS()), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Vertex");
        }

        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().SIGNI().attachable(CardUnderType.ATTACHED_SOUL)).get();

            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().under(getCardIndex())).get();
                
                attach(target, cardIndex, CardUnderType.ATTACHED_SOUL);
            }
        }
        
        private void onActionEff2()
        {
            crush(getOpponent());
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            EventAttack eventAttack = (EventAttack)getEvent();
            callDelayedEffect(eventAttack.requestPostAttackTrigger(), () -> {
                if(!eventAttack.didAttackDealDamage() && payEner(Cost.color(CardColor.RED, 1)))
                {
                    up();
                }
            });
        }
    }
}

