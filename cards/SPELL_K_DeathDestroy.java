package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class SPELL_K_DeathDestroy extends Card {

    public SPELL_K_DeathDestroy()
    {
        setImageSets("WXDi-P15-075");

        setOriginalName("デス・デストロイ");
        setAltNames("デスデストロイ Desu Desutoroi");
        setDescription("jp",
                "@[ベット]@ ― #C #C\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。あなたがベットしていた場合、代わりにターン終了時まで、それのパワーを－7000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Death Destroy");
        setDescription("en",
                "Bet -- #C #C \n\nTarget SIGNI on your opponent's field gets --2000 power until end of turn. If you made a bet, it gets --7000 power until end of turn instead." +
                "~#Target upped SIGNI on your opponent's field gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Death Destroy");
        setDescription("en_fan",
                "@[Bet]@ - #C #C\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power. If you bet, until end of turn, it gets --7000 power instead." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "死亡·毁灭");
        setDescription("zh_simplified", 
                "下注—#C #C（这张魔法使用时，可以作为使用费用追加把#C #C:支付）\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。你下注的场合，作为替代，直到回合结束时为止，其的力量-7000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            spell.setBetCost(new CoinCost(3));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), !spell.hasUsedBet() ? -2000 : -7000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
