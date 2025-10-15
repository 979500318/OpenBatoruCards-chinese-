package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
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

public final class LRIG_B3_DrTamagoNonstop extends Card {
    
    public LRIG_B3_DrTamagoNonstop()
    {
        setImageSets("WXDi-P04-009");
        
        setOriginalName("ノンストップ　Ｄｒ．タマゴ");
        setAltNames("ノンストップドクタータマゴ Nonsutoppu Dokutaa Tamago");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、このルリグの下からカード１枚をルリグトラッシュに置いてもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "@U：あなたの効果によって対戦相手が手札を１枚捨てたとき、対戦相手のシグニ１体を対象とし、それを凍結する。\n" +
                "@A $G1 %B0：各プレイヤーはカードを２枚引く。あなたは対戦相手の手札を２枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Nonstop! Dr. Tamago");
        setDescription("en",
                "@U: Whenever this LRIG attacks, you may put a card underneath this LRIG into its owner's LRIG trash. If you do, your opponent discards a card at random.\n" +
                "@U: Whenever your opponent discards a card by your effect, freeze target SIGNI on your opponent's field.\n" +
                "@A $G1 %B0: Each player draws two cards. Your opponent discards two cards at random."
        );
        
        setName("en_fan", "Dr. Tamago, Nonstop");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, you may put 1 card under this LRIG into the LRIG trash. If you do, choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "@U: Whenever your opponent discards 1 card by your effect, target 1 of your opponent's SIGNI, and freeze it.\n" +
                "@A $G1 %B0: Each player draws 2 cards. You choose 2 cards from your opponent's hand without looking, and discard them."
        );
        
		setName("zh_simplified", "无畏 Dr.玉子");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，可以从这只分身的下面把1张牌放置到分身废弃区。这样做的场合，不看对战对手的手牌选1张，舍弃。\n" +
                "@U :当因为你的效果把对战对手的手牌1张舍弃时，对战对手的精灵1只作为对象，将其冻结。\n" +
                "@A $G1 %B0:各玩家抽2张牌。你不看对战对手的手牌选2张，舍弃。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff1()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().under(getCardIndex())).get();
            
            if(trash(cardIndex))
            {
                cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
        
        private void onActionEff()
        {
            draw(2);
            draw(getOpponent(), 2);
            
            DataTable<CardIndex> data = playerChoiceHand(2);
            discard(data);
        }
    }
}
