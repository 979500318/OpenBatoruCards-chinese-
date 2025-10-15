package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.MillCost;
import open.batoru.data.ability.modifiers.AttackModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K3_IonaTriangleMaiden extends Card {
    
    public LRIG_K3_IonaTriangleMaiden()
    {
        setImageSets("WXDi-P08-010", "WXDi-P08-010U", Mask.IGNORE+"WXDi-P182P");
        
        setOriginalName("トライアングル／メイデン　イオナ");
        setAltNames("トライアングルメイデンイオナ Toraiangaru Meiden Iona");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。\n" +
                "@A $T1 @[デッキの上からカードを２枚トラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n" +
                "@A $G1 %K %X：次の対瀬相手のターンの間、対戦相手のシグニは可能ならばアタックしなければならない。そのターン終了時、そのターンにアタックしていたすべてのシグニをバニッシュする。"
        );
        
        setName("en", "Iona, Triangle/Maiden");
        setDescription("en",
                "@E: Put target SIGNI from your trash onto your field.\n" +
                "@A $T1 @[Put the top two cards of your deck into your trash]@: Target SIGNI on your opponent's field gets --2000 power until end of turn.\n" +
                "@A $G1 %K %X: During your opponent's next turn, SIGNI on your opponent's field must attack if able. At the end of that turn, vanish all SIGNI that attacked that turn."
        );
        
        setName("en_fan", "Iona, Triangle/Maiden");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your trash, and put it onto the field.\n" +
                "@A $T1 @[Put the top 2 cards of your deck into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power.\n" +
                "@A $G1 %K %X: During your opponent's next turn, your opponent's SIGNI must attack if able. At the end of that turn, banish all SIGNI that attacked that turn."
        );
        
		setName("zh_simplified", "三角/少女 伊绪奈");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把精灵1张作为对象，将其出场。\n" +
                "@A $T1 从牌组上面把2张牌放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "@A $G1 %K%X:下一个对战对手的回合期间，如果对战对手的精灵能攻击，则必须攻击。那个回合结束时，那个回合攻击过的全部的精灵破坏。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        
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
            
            ActionAbility act1 = registerActionAbility(new MillCost(2), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
        
        private void onActionEff2()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(), new AttackModifier(AttackModifierFlag.FORCE_ATTACK));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            
            callDelayedEffect(ChronoDuration.nextTurnEnd(getOpponent()), () -> {
                DataTable<CardIndex> dataAttacked = new DataTable<>();
                Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                 filter(e -> e.getId() == GameEventId.ATTACK && !isOwnCard(e.getCaller()) && CardType.isSIGNI(e.getCaller().getCardReference().getType())).forEachOrdered(e -> {
                    dataAttacked.add(e.getCallerCardIndex());
                });
                
                banish(dataAttacked);
            });
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
