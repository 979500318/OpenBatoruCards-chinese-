package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_MikomikoKirakkira extends Card {
    
    public LRIGA_B1_MikomikoKirakkira()
    {
        setImageSets("WXDi-P07-023");
        
        setOriginalName("みこみこ☆きらっきら");
        setAltNames("ミコミコキラッキラ Mikomiko Kirakkira");
        setDescription("jp",
                "@E：カードを３枚引き、手札を２枚捨てる。このターンに対戦相手の効果によってあなたの手札からカードが１枚以上トラッシュに移動していた場合、代わりにカードを５枚引き、手札を２枚捨てる。"
        );
        
        setName("en", "Mikomiko☆Kirakkira");
        setDescription("en",
                "@E: Draw three cards and discard two cards. If one or more cards were moved from your hand into your trash by your opponent's effect this turn, instead draw five cards and discard two cards."
        );
        
        setName("en_fan", "Mikomiko☆Kirakkira");
        setDescription("en_fan",
                "@E: Draw 3 cards, and discard 2 cards from your hand. If 1 or more cards were moved from your hand to your trash by your opponent's effect this turn, instead draw 5 cards, and discard 2 cards from your hand."
        );
        
		setName("zh_simplified", "美琴琴☆基拉基拉");
        setDescription("zh_simplified", 
                "@E :抽3张牌，手牌2张舍弃。这个回合因为对战对手的效果从你的手牌把牌1张以上往废弃区移动过的场合，作为替代，抽5张牌，手牌2张舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            draw(GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller()) &&
                event.getSourceAbility() != null && !isOwnCard(event.getSource())) < 1 ? 3 : 5
            );
            discard(2);
        }
    }
}
