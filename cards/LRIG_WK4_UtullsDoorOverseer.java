package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
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

public final class LRIG_WK4_UtullsDoorOverseer extends Card {

    public LRIG_WK4_UtullsDoorOverseer()
    {
        setImageSets("WXDi-P16-001B", "WXDi-P16-001BU");

        setOriginalName("扉の俯瞰者　ウトゥルス");
        setAltNames("トビラノフカンシャウトゥルス Tobira no Fukansha Uturusu Utulls");
        setDescription("jp",
                "@E：このルリグの下からカード１枚をルリグトラッシュに置く。対戦相手のシグニ１体を対象とし、それをゲームから除外する。\n" +
                "@A $T2 %X：あなたのトラッシュから白か黒のシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Ut'ulls, Gate Overseer");
        setDescription("en",
                "@E: Put a card underneath this LRIG into its owner's LRIG Trash. Remove target SIGNI on your opponent's field from the game.\n@A $T2 %X: Put target white or black SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Ut'ulls, Door Overseer");
        setDescription("en_fan",
                "@E: Put 1 card from under this LRIG into the LRIG trash. Target 1 of your opponent's SIGNI, and exclude it from the game.\n" +
                "@A $T2 %X: Target 1 white or black SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "扉的俯瞰者 乌托鲁斯");
        setDescription("zh_simplified", 
                "@E :从这只分身的下面把1张牌放置到分身废弃区。对战对手的精灵1只作为对象，将其从游戏除外。\n" +
                "@A $T2 %X:从你的废弃区把白色或黑色的精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL, CardLRIGType.UMR);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).own().under(getCardIndex())).get();
            trash(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.EXCLUDE).OP().SIGNI()).get();
            exclude(target);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.WHITE,CardColor.BLACK).fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
