package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_CodeArtCOrnDogMaker extends Card {

    public SIGNI_R1_CodeArtCOrnDogMaker()
    {
        setImageSets("WX25-P2-073");

        setOriginalName("コードアート　Aメリカンドッグメーカー");
        setAltNames("コードアートエーメリカンドッグメーカー Koodo Aato Eemerikan Doggu Meekaa American Dog Corndog");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたがスペルを使用していた場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。" +
                "~#：手札を１枚捨て、カードを３枚引く。"
        );

        setName("en", "Code Art C Orn Dog Maker");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used a spell this turn, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash." +
                "~#Discard 1 card from your hand, and draw 3 cards."
        );

		setName("zh_simplified", "必杀代号 玉米热狗机");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把魔法使用过的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。" +
                "~#手牌1张舍弃，抽3张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
                trash(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            discard(1);
            draw(3);
        }
    }
}
