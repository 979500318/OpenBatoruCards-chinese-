package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PlayerRuleBaseValueModifier;

public final class LRIG_WK4_MayuUnknownMiko extends Card {

    public LRIG_WK4_MayuUnknownMiko()
    {
        setImageSets("WXDi-P13-003B");

        setOriginalName("未知の巫女　マユ");
        setAltNames("ミチノミコマユ Michi no Miko Mayu");
        setDescription("jp",
                "@E：このルリグの下からカード１枚をルリグトラッシュに置く。ターン終了時まで、対戦相手のすべてのシグニは能力を失う。\n" +
                "@E：あなたはこのターンの次に、追加の１ターンを得る。そのターンの間、あなたはシグニを１体までしか場に出せない。"
        );

        setName("en", "Mayu, Unknown Miko");
        setDescription("en",
                "@E: Put a card underneath this LRIG into its owner's LRIG Trash. All SIGNI on your opponent's field lose their abilities until end of turn.\n@E: You gain an additional turn after this turn. During that turn, you can only have up to one SIGNI on your field. "
        );
        
        setName("en_fan", "Mayu, Unknown Miko");
        setDescription("en_fan",
                "@E: Put 1 card from under this LRIG into the LRIG trash. Until end of turn, all of your opponent's SIGNI lose their abilities.\n" +
                "@E: After this turn, you get 1 additional turn. During that turn, you can only have up to 1 SIGNI on your field."
        );

		setName("zh_simplified", "未知的巫女 茧");
        setDescription("zh_simplified", 
                "@E :从这只分身的下面把1张牌放置到分身废弃区。直到回合结束时为止，对战对手的全部的精灵的能力失去。\n" +
                "@E :你在这个回合之后，得到追加的1个回合。那个回合期间，你只能有精灵1只最多出场。（场上有2只以上的场合，你把精灵放置到废弃区，变为1只）\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA, CardLRIGType.IONA);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setLevel(4);
        setLimit(7);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().under(getCardIndex())).get();
            trash(target);

            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private void onEnterEff2()
        {
            int cachedTurn = getTurnCount();
            
            addPlayerRuleCheck(PlayerRuleCheckType.MUST_PASS_TURN_PLAYER, getOwner(), ChronoDuration.nextTurn(getOwner()), data -> RuleCheckState.BLOCK);
            
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleBaseValueModifier(PlayerRuleValueType.MAX_ALLOWED_SIGNI, TargetFilter.HINT_OWNER_OWN, 1));
            attachedConst.setCondition(() -> getTurnCount() != cachedTurn ? ConditionState.OK : ConditionState.BAD);
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd().repeat(2));
        }
    }
}

