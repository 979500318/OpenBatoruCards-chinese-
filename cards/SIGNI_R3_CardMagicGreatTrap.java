package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_CardMagicGreatTrap extends Card {

    public SIGNI_R3_CardMagicGreatTrap()
    {
        setImageSets("WX24-P4-045");

        setOriginalName("大罠　カードマジック");
        setAltNames("ダイビンカードマジック Daibin Kaado Majikku");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをライフクロスに加える。この方法でカードをライフクロスに加えた場合、ターン終了時まで、このシグニは【ダブルクラッシュ】を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Card Magic, Great Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may pay %R %X. If you do, add it to their life cloth. If a card was added to life cloth this way, until end of turn, this SIGNI gains [[Double Crush]].\n" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "大罠 卡牌魔术");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以支付%R%X。这样做的场合，将其加入生命护甲。这个方法把牌加入生命护甲的场合，直到回合结束时为止，这只精灵得到[[双重击溃]]。（攻击给予伤害则把生命护甲2张击溃）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(12000);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                if(addToLifeCloth(target))
                {
                    attachAbility(getCardIndex(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
                }
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
