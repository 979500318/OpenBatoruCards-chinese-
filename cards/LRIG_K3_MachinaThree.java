package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K3_MachinaThree extends Card {
    
    public LRIG_K3_MachinaThree()
    {
        setImageSets("WXDi-P04-016", Mask.IGNORE+"WXDi-P113");
        
        setOriginalName("マキナ・スリー");
        setAltNames("マキナスリー Makina Surii");
        setDescription("jp",
                "@U：[[ソウル]]が付いているあなたのシグニ１体がアタックしたとき、対戦相手のデッキの上からカードを２枚トラッシュに置く。\n" +
                "@A %K0：あなたのシグニ１体を対象とし、このルリグの下からカード１枚をそれの[[ソウル]]にする。\n" +
                "@A $G1 @[エクシード３]@：対戦相手のシグニを２体まで対象とし、あなたのルリグデッキにあるピース１枚をゲームから除外する。そうした場合、ターン終了時まで、それらのパワーをそれぞれ－12000する。"
        );
        
        setName("en", "Machina Three");
        setDescription("en",
                "@U: Whenever SIGNI on your field with a [[Soul]] attached to it attacks, put the top two cards of your opponent's deck into their trash.\n" +
                "@A %K0: Attach a card underneath this LRIG to target SIGNI on your field as a [[Soul]].\n" +
                "@A $G1 @[Exceed 3]@: Remove a PIECE in your LRIG Deck from the game. If you do, up to two target SIGNI on your opponent's field get --12000 power until end of turn."
        );
        
        setName("en_fan", "Machina Three");
        setDescription("en_fan",
                "@U: Whenever 1 of your SIGNI with a [[Soul]] attached to it attacks, put the top 2 cards of your opponent's deck into the trash.\n" +
                "@A %K0: Target 1 of your SIGNI, and attach 1 card under this LRIG to it as a [[Soul]].\n" +
                "@A $G1 @[Exceed 3]@: Target up to 2 of your opponent's SIGNI, and exclude 1 piece in your LRIG deck from the game. If you do, until end of turn, they get --12000 power."
        );
        
		setName("zh_simplified", "玛琪娜·叁");
        setDescription("zh_simplified", 
                "@U :当有[[灵魂]]附加的你的精灵1只攻击时，从对战对手的牌组上面把2张牌放置到废弃区。\n" +
                "@A %K0:你的精灵1只作为对象，从这只分身的下面把1张牌作为其的[[灵魂]]。\n" +
                "@A $G1 @[超越 3]@:对战对手的精灵2只最多作为对象，你的分身牌组的和音1张从游戏除外。这样做的场合，直到回合结束时为止，这些的力量各-12000。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            
            ActionAbility act2 = registerActionAbility(new ExceedCost(3), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_SOUL) > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            millDeck(getOpponent(), 2);
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BANISH).OP().SIGNI());
            
            if(data.get() != null)
            {
                CardIndex cardIndex = searchDeck(new TargetFilter(TargetHint.EXCLUDE).own().piece(), DeckType.LRIG).get();
                
                if(cardIndex != null && reveal(cardIndex) && exclude(cardIndex))
                {
                    gainPower(data, -12000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
