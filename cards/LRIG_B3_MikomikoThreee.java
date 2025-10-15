package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_MikomikoThreee extends Card {
    
    public LRIG_B3_MikomikoThreee()
    {
        setImageSets("WXDi-P05-014");
        
        setOriginalName("みこみこ☆さ～ん");
        setAltNames("ミコミコサーン Mikomiko Saan Mikomiko Three");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、対戦相手の手札が３枚以下である場合、カードを１枚引く。４枚以上ある場合、対戦相手は手札を１枚捨てる。\n" +
                "@E：対戦相手が手札を１枚捨てないかぎり、あなたはカードを２枚引く。\n" +
                "@A $G1 %B0：対戦相手の手札を見て１枚選び、ゲームから除外する。"
        );
        
        setName("en", "Mikomiko☆Threee");
        setDescription("en",
                "@U: Whenever this LRIG attacks, if your opponent has three or less cards in their hand, draw a card. If they have four or more cards in their hand, your opponent discards a card.\n" +
                "@E: Draw two cards unless your opponent discards a card.\n" +
                "@A $G1 %B0: Look at your opponent's hand and choose a card. Remove that card from the game."
        );
        
        setName("en_fan", "Mikomiko☆Three~e");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, if your opponent has 3 or less cards in their hand, draw 1 card. If they have 4 or more, your opponent discards 1 card from their hand.\n" +
                "@E: Unless your opponent discards 1 card from their hand, you draw 2 cards.\n" +
                "@A $G1 %B0: Look at your opponent's hand, and choose 1 card from it, and exclude it from the game."
        );
        
		setName("zh_simplified", "美琴琴☆叁～");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，对战对手的手牌在3张以下的场合，抽1张牌。4张以上的场合，对战对手把手牌1张舍弃。\n" +
                "@E :如果对战对手不把手牌1张舍弃，那么你抽2张牌。\n" +
                "@A $G1 %B0:看对战对手的手牌选1张，从游戏除外。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff()
        {
            if(getHandCount(getOpponent()) <= 3)
            {
                draw(1);
            } else {
                discard(getOpponent(), 1);
            }
        }
        
        private void onEnterEff()
        {
            if(discard(getOpponent(), 0,1).get() == null)
            {
                draw(2);
            }
        }
        
        private void onActionEff()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.EXCLUDE).OP().fromRevealed()).get();
            exclude(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
    }
}
