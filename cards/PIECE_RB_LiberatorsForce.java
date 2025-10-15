package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class PIECE_RB_LiberatorsForce extends Card {

    public PIECE_RB_LiberatorsForce()
    {
        setImageSets("WXDi-P15-001");

        setOriginalName("リベレーターズフォース");
        setAltNames("リベレーターズフォース Ribereetaazu Foosu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "カードを３枚引く。あなたの手札から＜解放派＞のシグニを１枚捨ててもよい。そうした場合、対戦相手のライフクロス１枚をクラッシュする。\n" +
                "このゲームの間、あなたのセンタールリグは以下の能力を得る。" +
                "@>@A @[エクシード４]@：あなたのトラッシュから＜解放派＞のシグニを２枚まで対象とし、それらをあなたの＜解放派＞のシグニ２体までの下に置く。"
        );

        setName("en", "Liberators' Force");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nDraw three cards. You may discard a <<Liberation Division>> SIGNI. If you do, crush one of your opponent's Life Cloth.\nYour Center LRIG gains the following ability for the duration of the game. \n@>@A @[Exceed 4]@: Put up to two target <<Liberation Division>> SIGNI from your trash under up to two <<Liberation Division>> SIGNI on your field."
        );
        
        setName("en_fan", "Liberators' Force");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Draw 3 cards. You may discard 1 <<Liberation Faction>> SIGNI from your hand. If you do, crush 1 of your opponent's life cloth.\n" +
                "This game, your center LRIG gains:" +
                "@>@A @[Exceed 4]@: Target up to 2 <<Liberation Faction>> SIGNI from your trash, and put them under up to 2 <<Liberation Faction>> SIGNI on your field."
        );

		setName("zh_simplified", "解放者原力");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "抽3张牌。可以从你的手牌把<<解放派>>精灵1张舍弃。这样做的场合，对战对手的生命护甲1张击溃。\n" +
                "这场游戏期间，你的核心分身得到以下的能力。（成长后的新的核心分身依然得到能力）\n" +
                "@>@A @[超越 4]@从你的废弃区把<<解放派>>精灵2张最多作为对象，将这些放置到你的<<解放派>>精灵2只最多的下面。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.RED, CardColor.BLUE);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            draw(3);
            
            if(discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION)).get() != null)
            {
                crush(getOpponent());
            }

            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff);
        }
        private void onAttachedActionEff()
        {
            DataTable<CardIndex> dataFromTrash = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromTrash());
            
            if(dataFromTrash.get() != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION));
                
                attach(data,dataFromTrash, CardUnderType.UNDER_GENERIC);
            }
        }
    }
}
