package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
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
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_X3_EternalA extends Card {

    public LRIG_X3_EternalA()
    {
        setImageSets("WXDi-P11-010B", "WXDi-P11-010BU");

        setOriginalName("夢限　－Ａ－");
        setAltNames("ムゲンアンサー Mugen Ansaa Answer Mugen A");
        setDescription("jp",
                "@U：このルリグが《夢限　－Ｑ－》から《夢限　－Ａ－》になったとき、カードを５枚引き【エナチャージ５】をする。\n" +
                "@A $T1 %X：対戦相手のシグニ１体を対象とし、それをゲームから除外する。"
        );

        setName("en", "Mugen -A-");
        setDescription("en",
                "@U: When this LRIG has changed from \"Mugen -Q-\" to \"Mugen -``A-\", draw five cards and [[Ener Charge 5]].\n" +
                "@A $T1 %X: Remove target SIGNI on your opponent's field from the game."
        );
        
        setName("en_fan", "Eternal -A-");
        setDescription("en_fan",
                "@U: When this LRIG becomes \"Eternal -``A-\" from \"Eternal -Q-\", draw 5 cards and [[Ener Charge 5]].\n" +
                "@A $T1 %X: Target 1 of your opponent's SIGNI, and exclude it from the game."
        );

		setName("zh_simplified", "梦限 -A-");
        setDescription("zh_simplified", 
                "@U :当这只分身从《夢限　-Q-》变为《夢限　-A-》时，抽5张牌并[[能量填充5]]。\n" +
                "@A $T1 %X:对战对手的精灵1只作为对象，将其从游戏除外。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUGEN);
        setLevel(3);
        setLimit(9);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.TRANSFORM, this::onAutoEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private void onAutoEff()
        {
            draw(5);
            enerCharge(5);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.EXCLUDE).OP().SIGNI()).get();
            exclude(target);
        }
    }
}
