package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K1_OreoreSmallTrap extends Card {

    public SIGNI_K1_OreoreSmallTrap()
    {
        setImageSets("WXK01-067");

        setOriginalName("小罠　オレオレ");
        setAltNames("ショウビンオレオレ Shoubin Oreore");
        setDescription("jp",
                "@U：このカードがあなたのデッキからトラッシュに置かれたとき、対戦相手のシグニ１体を対象とし、あなたのターンの場合、ターン終了時まで、それのパワーを－2000する。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Oreore, Small Trap");
        setDescription("en",
                "@U: When this SIGNI is put from your deck into the trash, target 1 of your opponent's SIGNI, and if it is your turn, until end of turn, it gets --2000 power." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "小罠 电汇欺诈");
        setDescription("zh_simplified", 
                "@U :当这张牌从你的牌组放置到废弃区时，对战对手的精灵1只作为对象，你的回合的场合，直到回合结束时为止，其的力量-2000。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setActiveLocation(CardLocation.DECK_MAIN);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && isOwnTurn())
            {
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
