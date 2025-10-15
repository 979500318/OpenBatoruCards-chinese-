package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R3_TawilTreTheHerald extends Card {
    
    public LRIG_R3_TawilTreTheHerald()
    {
        setImageSets("WXDi-P05-015", Mask.IGNORE+"WXDi-P116");
        
        setOriginalName("掲げし者　タウィル＝トレ");
        setAltNames("カカゲシモノタウィルトレ Kakage Shimono Tauiru Tore");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に赤のシグニが３体以上ある場合、[[エナチャージ１]]をする。\n" +
                "@A $G1 %R0：あなたのデッキの上からカードを６枚見て、その中からカード３枚を表向きの束にし、残りを裏向きの束にする。対戦相手はどちらかの束をトラッシュに置き、あなたは残りの束を手札に加える。"
        );
        
        setName("en", "Tawil =Tre=, Heralding One");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are three or more red SIGNI on your field, [[Ener Charge 1]].\n" +
                "@A $G1 %R0: Look at the top six cards of your deck and separate them into a three-card face-up pile and a face-down pile. Your opponent puts one pile into your trash and the other pile is added to your hand."
        );
        
        setName("en_fan", "Tawil-Tre, The Herald");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more red SIGNI on your field, [[Ener Charge 1]].\n" +
                "@A $G1 %R0: Look at the top 6 cards of your deck, place 3 of those cards as a face-up pile, and place the rest as a face-down pile. Your opponent puts one of those piles into the trash, and you add the remaining pile to your hand."
        );
        
		setName("zh_simplified", "揭竿者 塔维尔=TRE");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的红色的精灵在3只以上的场合，[[能量填充1]]。\n" +
                "@A $G1 %R0:从你的牌组上面看6张牌，从中把3张牌表向分为1份，剩下的里向分为1份。对战对手把其中的1份放置到废弃区，你把剩下的1份加入手牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.RED).getValidTargetsCount() >= 3)
            {
                enerCharge(1);
            }
        }
        
        private void onActionEff()
        {
            look(6);
            
            DataTable<CardIndex> data = playerTargetCard(Math.min(3, getLookedCount()), new TargetFilter(TargetHint.REVEAL).own().fromLooked());
            reveal(data, true);
            
            if(playerChoiceAction(getOpponent(), ActionHint.FACE_UP, ActionHint.FACE_DOWN) == 1)
            {
                trash(getCardsInRevealed(getOwner()));
                addToHand(getCardsInLooked(getOwner()));
            } else {
                trash(getCardsInLooked(getOwner()));
                addToHand(getCardsInRevealed(getOwner()));
            }
        }
    }
}
